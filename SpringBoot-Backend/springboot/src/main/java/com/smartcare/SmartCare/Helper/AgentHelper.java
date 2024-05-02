package com.smartcare.SmartCare.Helper;

import com.smartcare.SmartCare.DTO.AgentDTO;
import com.smartcare.SmartCare.DTO.AgentResponse;
import com.smartcare.SmartCare.Model.Agent;
import com.smartcare.SmartCare.Model.Owner;
import com.smartcare.SmartCare.Repository.AgentRepo;

import java.util.Date;

public class AgentHelper {
    public static Agent convertIntoAgent(AgentDTO agentDTO,Agent agent){
        agent.setAgentId(agentDTO.getAgentId());
        agent.setName(agentDTO.getName());
        agent.setPhoneNumber(agentDTO.getPhoneNumber());
        agent.setEmail(agentDTO.getEmail());
        agent.setAddress(agentDTO.getAddress());
        agent.setPassword(agentDTO.getPassword());
        agent.setCreatedAt(new Date());
        Owner owner = new Owner();
        owner.setOwnerId(agentDTO.getOwnerId());
        agent.setOwner(owner);
        return agent;
    }
    public static AgentResponse setAgentResponse(Agent agent, AgentResponse agentResponse){
        agentResponse.setAgentId(agent.getAgentId());
        agentResponse.setNgoId(agent.getOwner().getNgoId());
        agentResponse.setName(agent.getName());
        agentResponse.setEmail(agent.getEmail());
        agentResponse.setPhoneNumber(agent.getPhoneNumber());
        agentResponse.setAddress(agent.getAddress());
        agentResponse.setCreateAt(agent.getCreatedAt());
        return agentResponse;
    }
}
