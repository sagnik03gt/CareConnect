package com.smartcare.SmartCare.Redis.Helper;

import com.smartcare.SmartCare.Model.Customer;
import com.smartcare.SmartCare.Redis.Model.RedisCustomer;

public class RedisCustomerHelper {
    public static RedisCustomer convertIntoRedisCustomer(RedisCustomer redisCustomer, Customer customer){
        redisCustomer.setUserId(customer.getUserId());
        redisCustomer.setUsername(customer.getUsername());
        redisCustomer.setAddress(customer.getAddress());
        redisCustomer.setDob(customer.getDob());
        redisCustomer.setEmail(customer.getEmail());
        redisCustomer.setCreatedAt(customer.getCreatedAt());
        redisCustomer.setPhoneNumber(customer.getPhoneNumber());
        redisCustomer.setPassword(customer.getPassword());
        return redisCustomer;
    }
}
