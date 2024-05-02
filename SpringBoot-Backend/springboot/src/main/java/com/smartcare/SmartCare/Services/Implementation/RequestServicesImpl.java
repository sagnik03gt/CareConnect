package com.smartcare.SmartCare.Services.Implementation;

import com.smartcare.SmartCare.DTO.NgoWithKms;
import com.smartcare.SmartCare.Kafka.Config.AppConstants;
import com.smartcare.SmartCare.Model.Customer;
import com.smartcare.SmartCare.Model.HelpList;
import com.smartcare.SmartCare.Redis.Model.RedisHelpList;
import com.smartcare.SmartCare.Repository.ActiveAgentRepo;
import com.smartcare.SmartCare.Repository.HelpListRepo;
import com.smartcare.SmartCare.Repository.OwnerRepo;
import com.smartcare.SmartCare.Services.RequestServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.args.GeoUnit;
import redis.clients.jedis.resps.GeoRadiusResponse;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class RequestServicesImpl implements RequestServices {


    //depends on the decision has benn made by board members.
    private final double rangeForFindingNearestNgo = 5;

    @Autowired
    private UnifiedJedis jedis;
    @Autowired
    private OwnerServiceImpl geoSearch;

    @Autowired
    private ActiveAgentRepo activeAgentRepo;

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    @Autowired
    private HelpListRepo helpListRepo;


    @Autowired
    private OwnerRepo ownerRepo;

    @Autowired
    private RedisTemplate redisTemplate;

    private final String hashKeyForRequestSentNgo = "RequestSentNgo";

    private Logger log = LoggerFactory.getLogger(RequestServicesImpl.class);

    //using user's longitude and latitude and redis geo search features, we can find out nearest ngo's
    //store the response as a list and sent to the front end part
    @Override
    public List<String> findNearestNgoByLongLat(double longitude, double latitude) {
        List<GeoRadiusResponse> nearestNgo = jedis.geosearch
                (geoSearch.getHashKeyForNgoLocation(),
                        new GeoCoordinate(longitude, latitude),
                        rangeForFindingNearestNgo,
                        GeoUnit.KM);
        return nearestNgo.stream()
                .map(GeoRadiusResponse::getMemberByString)
                .collect(Collectors.toList());
    }
    // enhancing the ux we have calculated the distance between user and multiple nearest ngo's
    @Override
    public double calculateDistanceBetweenTwoLongLat(double long1, double lat1, double long2, double lat2) {
        long1 = Math.toRadians(long1);
        long2 = Math.toRadians(long2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);


        // some mathematical calculation that is copied from chatgpt don't need to understand, if you wish you can.
        double dlon = long2 - long1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        double r = 6371;
        return (c*r);
    }

    // we have to  process the sending request to given ngo

    @Override
    public List<NgoWithKms> sentNearestNgoWithDistance(double longitude, double latitude) {

        //first of all find all nearest ngo and store them as list
        List<String> nearestNgoByLongLat = findNearestNgoByLongLat(longitude, latitude);
        //this list is used to store ngo name and distance from user location
        List<NgoWithKms> finalResultOfNgoWithKms = new ArrayList<>();


        //for debugging purpose, print or log the size nearest ngo's
        log.info(String.valueOf(nearestNgoByLongLat.size()));

        //this is the time, where we have to map the ngo name and distance and sent to front end part
        if(!nearestNgoByLongLat.isEmpty()) {
            for (int i = 0; i < nearestNgoByLongLat.size(); i++) {
                NgoWithKms ngoWithKms = new NgoWithKms();

                //getting the ngo's from nearestNgoByLongLat list
                String ngoId = nearestNgoByLongLat.get(i);
                log.info(ngoId);

                //finding the longitude and latitude form owner table information where ngo location has been stored
                Map<String, String> longLatByNgoId = ownerRepo.findLongLatByNgoId(ngoId);

                //getting them separately
                String longitude1 = longLatByNgoId.get("longitude");
                String latitude1 = longLatByNgoId.get("latitude");

                //calculating the distance using above longitude and latitude
                double distanceFromUserCurrentLocation = calculateDistanceBetweenTwoLongLat(longitude, latitude, Double.parseDouble(longitude1), Double.parseDouble(latitude1));

                //mapping the result with NgoWithKms class
                ngoWithKms.setNgoName(ngoId);
                ngoWithKms.setDistance(distanceFromUserCurrentLocation);

                //as it is declared in the first, finally one result has completed, so
                // it is time to add the result into finalResultOfNgoWithKms list
                finalResultOfNgoWithKms.add(i, ngoWithKms);
            }
            return finalResultOfNgoWithKms;
        }
        throw new RuntimeException("No Nearest Ngo is found");

    }

    @Override
    public String bookedNgo(String ngoId, String custId,String lon,String lat) {
        int totalActiveNgoMembers = activeAgentRepo.totalActiveNgoMembers(ngoId);


       // this is only for redis template usage
        Customer customer = new Customer();
        customer.setUserId(custId);

        if(totalActiveNgoMembers > 0){
            //first store this helping details into redis for fast speed
            //after the agent submit complete or done request then only the details has been stored into our database;
            RedisHelpList redisHelpList = new RedisHelpList();
            redisHelpList.setRequestDate(new Date());
            redisHelpList.setSolvedTime(null);
            redisHelpList.setLongitude(lon);
            redisHelpList.setLatitude(lat);
            redisHelpList.setStatus("OPEN");
            redisHelpList.setCustomerId(custId);
            redisHelpList.setNgoId(ngoId);
            //saving the details with customer id into redis
            redisTemplate.opsForHash().put(hashKeyForRequestSentNgo,custId,redisHelpList);

            //sending the request to ngo's owner and active agent using kafka
            //kafka -> prevent uninterrupted database failure.
            kafkaTemplate.send(AppConstants.RequestTopicName,custId);
            return "Booked ";
        }
        throw new RuntimeException("there is no one available into this ngo..!!");
    }

    @Override
    public Object viewRequestByCustId(String id) {
        return redisTemplate.opsForHash().get(hashKeyForRequestSentNgo,id);
    }

    @Override
    public Object markedRequestAsClosed(String id) {
        //getting the user request details from redis
        RedisHelpList list = (RedisHelpList) redisTemplate.opsForHash().get(hashKeyForRequestSentNgo, id);
        //updating the status and solved time and again saved into redis
        list.setStatus("ClOSED");
        list.setSolvedTime(new Date());
        //saving into redis
        redisTemplate.opsForHash().put(hashKeyForRequestSentNgo, id, list);


        //prepare user request to save into mysql database
        HelpList helpList = new HelpList();
        helpList.setRequestDate(list.getRequestDate());
        helpList.setLongitude(list.getLongitude());
        helpList.setLatitude(list.getLatitude());
        helpList.setStatus(list.getStatus());
        helpList.setSolvedTime(list.getSolvedTime());helpList.setNgoId(list.getNgoId());
        Customer customer = new Customer();
        customer.setUserId(list.getCustomerId());
        helpList.setCustomer(customer);
        helpListRepo.save(helpList);
        redisTemplate.opsForHash().delete(hashKeyForRequestSentNgo, id);
        log.info(String.valueOf(list));
        return "Request Status has been updated as closed. ";
    }

    @Override
    public Object allRequest(String type, String id) {
        if(type.equals("Agent") || type.equals("Owner")){
            return helpListRepo.findByngoId(id);
        }
        return helpListRepo.findRequestByUserId(id);
    }

}
