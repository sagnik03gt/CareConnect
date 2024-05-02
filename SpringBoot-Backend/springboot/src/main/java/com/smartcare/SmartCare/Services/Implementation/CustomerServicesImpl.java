package com.smartcare.SmartCare.Services.Implementation;

import com.smartcare.SmartCare.DTO.CustomerDTO;
import com.smartcare.SmartCare.DTO.CustomerDTOUpdate;
import com.smartcare.SmartCare.Helper.CustomerHelper;
import com.smartcare.SmartCare.Model.Customer;
import com.smartcare.SmartCare.Redis.Helper.RedisCustomerHelper;
import com.smartcare.SmartCare.Redis.Model.RedisCustomer;
import com.smartcare.SmartCare.Repository.CustomerRepo;
import com.smartcare.SmartCare.Services.CustomerServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServicesImpl implements CustomerServices {
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private RedisTemplate redisTemplate;
    public static final String HashKeyForCustomer = "customer";

    private Logger log = LoggerFactory.getLogger(CustomerServicesImpl.class);
    @Override
    public Object saveCust(CustomerDTO customerDTO) {
        log.info("saving new customer");
        Customer savedCustomer = customerRepo.save(CustomerHelper.convertIntoCustomer(customerDTO, new Customer()));
        log.info("saved to mysql");
        redisTemplate.opsForHash().put(HashKeyForCustomer,savedCustomer.getUserId(), RedisCustomerHelper.convertIntoRedisCustomer(new RedisCustomer(),savedCustomer));
        log.info("saved to redis");
        return savedCustomer;
    }

    @Override
    public Object updateCust(CustomerDTOUpdate customerDTOUpdate) {
        if(!customerRepo.existsById(customerDTOUpdate.getUserId())){
            throw new RuntimeException("user not valid");
        }
        Customer customerRepoByuserId = customerRepo.findByuserId(customerDTOUpdate.getUserId());
        redisTemplate.opsForHash().put(HashKeyForCustomer,customerDTOUpdate.getUserId(),RedisCustomerHelper.convertIntoRedisCustomer(new RedisCustomer(),customerRepoByuserId));
        log.info("updated to redis");
        return customerRepo.save(CustomerHelper.convertIntoCustomerUpdate(customerDTOUpdate, customerRepoByuserId));

    }

    @Override
    public Object viewCust(String userId) {
        Object foundFromRedis = redisTemplate.opsForHash().get(HashKeyForCustomer, userId);
        if(true){
            if(foundFromRedis == null){
                log.info("found from sql");
                return customerRepo.findByuserId(userId);
            }
            else{
                log.info("found from redis");
                return foundFromRedis;
            }
        }
        throw new RuntimeException();
    }

    @Override
    public String deleteCust(String userId) {
        if(customerRepo.existsById(userId)){
            redisTemplate.opsForHash().delete(HashKeyForCustomer,userId);
            log.info("delete from redis");
            customerRepo.deleteById(userId);
            log.info("delete from sql");
            return "deleted : " + userId;
        }
        throw new RuntimeException("User not valid");
    }

    @Override
    public String checkEmail(String email) {
        return customerRepo.existsByemail(email) ? "Email Not Available " :"Email Available " ;
    }
}
