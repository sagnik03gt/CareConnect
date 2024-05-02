package com.smartcare.SmartCare.Services.Implementation;

import com.smartcare.SmartCare.DTO.OwnerDTO;
import com.smartcare.SmartCare.Helper.OwnerHelper;
import com.smartcare.SmartCare.Model.Agent;
import com.smartcare.SmartCare.Model.Owner;
import com.smartcare.SmartCare.Redis.Helper.RedisOwnerHelper;
import com.smartcare.SmartCare.Redis.Model.RedisOwner;
import com.smartcare.SmartCare.Repository.ActiveAgentRepo;
import com.smartcare.SmartCare.Repository.AgentRepo;
import com.smartcare.SmartCare.Repository.OwnerRepo;
import com.smartcare.SmartCare.Services.OwnerServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.args.GeoUnit;
import redis.clients.jedis.resps.GeoRadiusResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OwnerServiceImpl implements OwnerServices {
    @Autowired
    private OwnerRepo ownerRepo;

    @Autowired
    private AgentRepo agentRepo;

    @Autowired
    private ActiveAgentRepo activeAgentRepo;
    @Value("${upload.path}")
    private String pathToSavedAadharCard;

    private final String HashKeyForOwner = "owner";
    private final String HashKeyForNgoLocation = "StoreNgoLongLat";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UnifiedJedis jedis;

    private Logger log = LoggerFactory.getLogger(OwnerServiceImpl.class);

    public String getHashKeyForNgoLocation(){
        return HashKeyForNgoLocation;
    }

    //storing aadhar card for privacy purpose
    @Override
    public Boolean saveAadharCardToLocalStorage(MultipartFile file, String ngoId) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path filePath = Paths.get(pathToSavedAadharCard, ngoId.concat(".jpg"));

        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
            outputStream.write(file.getBytes());
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public Object saveOwner(OwnerDTO ownerDTO) {
        Owner owner = ownerRepo.save(OwnerHelper.convertIntoOwner(ownerDTO, new Owner()));
        redisTemplate.opsForHash().put(HashKeyForOwner, owner.getOwnerId(), RedisOwnerHelper.convertIntoRedisOwner(new RedisOwner(), owner));
        log.info("saved to owner to db, ngo id is : " + ownerDTO.getNgoId());
        //longitude and latitude has used for getting nearest ngo from user location
        double longitude = Double.parseDouble(ownerDTO.getLongitude());
        double latitude = Double.parseDouble(ownerDTO.getLatitude());

        jedis.geoadd(HashKeyForNgoLocation, longitude, latitude, ownerDTO.getNgoId());
        log.info("ngo info saved to redis longitude : " + ownerDTO.getLongitude() + " latitude : " + ownerDTO.getLatitude());
        return owner;
    }

    @Override
    public Object viewOwner(String userId) {
        Object foundFromRedisOwner = redisTemplate.opsForHash().get(HashKeyForOwner, userId);
        if (foundFromRedisOwner == null) {
            log.info("found owner from  db, id is : " + userId);
            return ownerRepo.findByownerId(userId);
        } else if (foundFromRedisOwner != null) {
            log.info("found owner from redis");
            return foundFromRedisOwner;
        }
        throw new RuntimeException("user not exists");
    }

    @Override
    public String deleteOwner(String userId) {
        if (ownerRepo.existsById(userId)) {
            redisTemplate.opsForHash().delete(HashKeyForOwner, userId);
            String ngoId = ownerRepo.findByownerId(userId).getNgoId();
            redisTemplate.opsForGeo().remove(HashKeyForNgoLocation, ngoId);
            ownerRepo.deleteById(userId);
            return "deleted " + userId;
        }
        throw new RuntimeException("user not exists");
    }

    @Override
    public String checkNgoId(String email) {
        return ownerRepo.existsByngoId(email) ? "NGO Already Registered" : "NGO Not Registered";
    }

    @Override
    public List<Map<String, Object>> listAllAgentByOwnerId(String id) {
        log.info(id);
        return ownerRepo.findAllAgentByOwnerId(id);
    }

    @Override
    public Object findNgo(String id) {
        if(id.contains("ngo")){
            return ownerRepo.findNgobyNgoId(id);
        }
        return ownerRepo.findNgobyOwnerId(id);
    }

    @Override
    public int totalNgoMembers(String ownerId) {
        return ownerRepo.totalNgoMembers(ownerId);
    }

    @Override
    public List<Map<String, Object>> findAllActiveMembers(String ngoId) {

        //this helps to fetch the all online agent who is ready give services to people
        Map<String,Object> countActiveMember = new HashMap<>();
        countActiveMember.put("Active Members ",activeAgentRepo.totalActiveNgoMembers(ngoId));

        List<String> activeMembersId = activeAgentRepo.getActiveMembersId(ngoId,"ACTIVE");
        List<Map<String, Object>> response = new ArrayList<>();
        response.add(countActiveMember);
        for(String id : activeMembersId){
            log.info(id);
            Map<String, Object> activeMembers = agentRepo.getActiveMembersNameAndPhone(id);
            response.add(activeMembers);
        }

        return response;
    }
    // getting the aadhar card from device
    @Override
    public Resource viewAadharCard(String ngoId) throws MalformedURLException, FileNotFoundException {
        Path filePath = Paths.get(pathToSavedAadharCard).resolve(ngoId);
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new FileNotFoundException(ngoId + " ngo owner has not submitted aadhar card yet");
        }
    }
}