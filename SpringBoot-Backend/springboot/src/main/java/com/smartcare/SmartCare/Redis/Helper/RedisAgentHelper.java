package com.smartcare.SmartCare.Redis.Helper;

import com.smartcare.SmartCare.Model.Agent;
import com.smartcare.SmartCare.Redis.Model.RedisAgent;

public class RedisAgentHelper {
    public static RedisAgent convertIntoRedisAgent(RedisAgent redisAgent, Agent agent){
        redisAgent.setAgentId(agent.getAgentId());
        redisAgent.setOwnerId(agent.getOwner().getOwnerId());
        redisAgent.setName(agent.getName());
        redisAgent.setPhoneNumber(agent.getPhoneNumber());
        redisAgent.setEmail(agent.getEmail());
        redisAgent.setAddress(agent.getAddress());
        redisAgent.setCreatedAt(agent.getCreatedAt());
        return redisAgent;

    }
}
