package com.smartcare.SmartCare.Repository;

import com.smartcare.SmartCare.Model.ActiveAgents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ActiveAgentRepo extends JpaRepository<ActiveAgents,Integer> {
    @Query(value = "select count(agent_id) from active_agents where ngo_id=:id",nativeQuery = true)
    int totalActiveNgoMembers(String id);

    @Query(value = "select agent_id from active_agents where ngo_id=:id and status =:status",nativeQuery = true)
    List<String> getActiveMembersId(String id,String status);

    @Query(value = "select status from active_agents where ngo_id=:id",nativeQuery = true)
    String getStatus(String id);

    ActiveAgents findByagentId(String id);


}
