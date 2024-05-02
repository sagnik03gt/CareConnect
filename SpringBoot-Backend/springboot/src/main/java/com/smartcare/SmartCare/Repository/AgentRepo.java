package com.smartcare.SmartCare.Repository;

import com.smartcare.SmartCare.Model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface AgentRepo extends JpaRepository<Agent,String> {
    Boolean existsByagentId(String id);
    @Query(value = "select agent_id from agent where owner_id =:ownerId order by agent_id desc limit 1",nativeQuery = true)
    String latestAgentId(String ownerId);


    @Query(value = "select password from agent where agent_id=:username",nativeQuery = true)
    String getPassByAgentId(String username);

    @Query(value = "select name,phone_number from agent where agent_id=:id",nativeQuery = true)
    Map<String,Object> getActiveMembersNameAndPhone(String id);
}
