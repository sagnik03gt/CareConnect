package com.smartcare.SmartCare.Services.Implementation;

import com.smartcare.SmartCare.DTO.AgentDTO;
import com.smartcare.SmartCare.DTO.AgentResponse;
import com.smartcare.SmartCare.Helper.AgentHelper;
import com.smartcare.SmartCare.Model.ActiveAgents;
import com.smartcare.SmartCare.Model.Agent;
import com.smartcare.SmartCare.Model.AgentLogInHistory;
import com.smartcare.SmartCare.Model.Owner;
import com.smartcare.SmartCare.Redis.Helper.RedisAgentHelper;
import com.smartcare.SmartCare.Redis.Model.RedisAgent;
import com.smartcare.SmartCare.Repository.ActiveAgentRepo;
import com.smartcare.SmartCare.Repository.AgentLogInHistoryRepo;
import com.smartcare.SmartCare.Repository.AgentRepo;
import com.smartcare.SmartCare.Services.AgentServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class AgentServicesImpl implements AgentServices {
    @Autowired
    private AgentRepo agentRepo;

    @Autowired
    private ActiveAgentRepo activeAgentRepo;

    @Autowired
    private AgentLogInHistoryRepo agentLogInHistoryRepo;

    @Autowired
    private RedisTemplate redisTemplate;

    private final String hashKeyForAgent="agentKey";

    private Logger log = LoggerFactory.getLogger(AgentServicesImpl.class);

    @Override
    public Object saveAgent(AgentDTO agentDTO) {
        Agent savedAgent = agentRepo.save(AgentHelper.convertIntoAgent(agentDTO, new Agent()));
        log.info("saved " + agentDTO.getAgentId() + " to sql ");
        redisTemplate.opsForHash().put(hashKeyForAgent,agentDTO.getAgentId(), RedisAgentHelper.convertIntoRedisAgent(new RedisAgent(),savedAgent));
        log.info("saved " + agentDTO.getAgentId() + " to redis");
        return AgentHelper.setAgentResponse(savedAgent,new AgentResponse());
    }

    @Override
    public Object viewAgent(String agentId) {
        Object foundAgentFromRedis = redisTemplate.opsForHash().get(hashKeyForAgent, agentId);
        if(foundAgentFromRedis == null){
            log.info("found  " + agentId + "from  sql");
            return agentRepo.findById(agentId);
        }
        else if(foundAgentFromRedis != null){
            log.info("found  " + agentId + "from  redis");
            return foundAgentFromRedis;
        }
        throw new RuntimeException("agent not exists");
    }

    @Override
    public String deleteAgent(String agentId) {
        redisTemplate.opsForHash().delete(hashKeyForAgent,agentId);
        log.info("deleted " + agentId + " from redis");
        if(agentRepo.existsByagentId(agentId)){
            agentRepo.deleteById(agentId);
            log.info("deleted " + agentId + " from sql");
            return "deleted id : " + agentId;
        }
        throw new RuntimeException("agent not exists");
    }

    @Override
    public String generateNextAgentId(String ownerId){
        String latestAgentId = agentRepo.latestAgentId(ownerId);
        int trimmedId = Integer.parseInt(latestAgentId.substring(3)) ;
        return latestAgentId.replace(String.valueOf(trimmedId),String.valueOf(trimmedId +1));
    }
    @Override
    public Boolean logIn(String username,String password,String ngoId){
        if(agentRepo.existsByagentId(username)){  //checks whether the agents exist or not
            if((agentRepo.getPassByAgentId(username).equals(password)) ) { //checking given password with stored password
                try{
                    String agentStatus = activeAgentRepo.getStatus(username);
                    if (agentStatus.equals("DEACTIVE")) { // find whether agent has already logged in or not and it means that agent is new

                        //storing logging details
                        AgentLogInHistory agentLogInHistory = new AgentLogInHistory();
                        agentLogInHistory.setDateTime(new Date());
                        agentLogInHistory.setNgoId(ngoId);
                        agentLogInHistory.setAgentId(username);
                        agentLogInHistoryRepo.save(agentLogInHistory);

                        // stored agent information to sent request all online agent
                        ActiveAgents activeAgents = new ActiveAgents();
                        activeAgents.setStatus("ACTIVE");
                        activeAgents.setNgoId(ngoId);
                        activeAgents.setAgentId(username);
                        activeAgentRepo.save(activeAgents);
                        return true;
                    }
                }
                catch (Exception e){

                    //same as previous but only for whose status is DEACTIVE
                    AgentLogInHistory agentLogInHistory = new AgentLogInHistory();
                    agentLogInHistory.setDateTime(new Date());
                    agentLogInHistory.setNgoId(ngoId);
                    agentLogInHistory.setAgentId(username);
                    agentLogInHistoryRepo.save(agentLogInHistory);
                    ActiveAgents activeAgents = new ActiveAgents();
                    activeAgents.setStatus("ACTIVE");
                    activeAgents.setNgoId(ngoId);
                    activeAgents.setAgentId(username);
                    activeAgentRepo.save(activeAgents);
                    return true;
                }

            }
        }
        return false;
    }

    @Override
    public String logout(String username, String password, String ngoId) {
        if(agentRepo.existsByagentId(username)){
            if(agentRepo.getPassByAgentId(username).equals(password)){
                ActiveAgents activeAgentsFromDb = activeAgentRepo.findByagentId(username);
                activeAgentsFromDb.setStatus("DEACTIVE");
                activeAgentRepo.save(activeAgentsFromDb);
                return "logged out";
            }
        }
        return "failed to log out";
    }
}
