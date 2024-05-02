package com.smartcare.SmartCare.Repository;

import com.smartcare.SmartCare.Model.Agent;
import com.smartcare.SmartCare.Model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OwnerRepo extends JpaRepository<Owner,String> {
    Boolean existsByngoId(String id);
    Owner findByownerId(String id);
    @Query(value = "select * from agent where owner_id =:id",nativeQuery = true)
    List<Map<String,Object>> findAllAgentByOwnerId(String id);

    @Query(value = "select longitude,latitude from owner where ngo_id =:id",nativeQuery = true)
    Map<String,String> findLongLatByNgoId(String id);

    @Query(value = "select ngo_id,ngo_address,name,longitude,latitude from owner where ngo_id =:id",nativeQuery = true)
    Map<String,String> findNgobyNgoId(String id);

    @Query(value = "select ngo_id,ngo_address,name,longitude,latitude from owner where owner_id =:id",nativeQuery = true)
    Map<String,String> findNgobyOwnerId(String id);

    @Query(value = "select count(agent_id) from agent where owner_id=:id",nativeQuery = true)
    int totalNgoMembers(String id);
}
