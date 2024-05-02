package com.smartcare.SmartCare.Redis.Helper;

import com.smartcare.SmartCare.Model.Owner;
import com.smartcare.SmartCare.Redis.Model.RedisOwner;

public class RedisOwnerHelper {
    public static RedisOwner convertIntoRedisOwner(RedisOwner redisOwner, Owner owner){
        redisOwner.setOwnerId(owner.getOwnerId());
        redisOwner.setName(owner.getName());
        redisOwner.setAddress(owner.getAddress());
        redisOwner.setDob(owner.getDob());
        redisOwner.setEmail(owner.getEmail());
        redisOwner.setPhoneNumber(owner.getPhoneNumber());
        redisOwner.setNgoId(owner.getNgoId());
        redisOwner.setNgoAddress(owner.getNgoAddress());
        redisOwner.setLongitude(owner.getLongitude());
        redisOwner.setLatitude(owner.getLatitude());
        redisOwner.setPassword(owner.getPassword());
        redisOwner.setCreatedAt(owner.getCreatedAt());
        return redisOwner;
    }
}
