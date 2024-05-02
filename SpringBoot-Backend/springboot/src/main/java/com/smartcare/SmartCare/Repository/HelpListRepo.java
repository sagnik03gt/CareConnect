package com.smartcare.SmartCare.Repository;

import com.smartcare.SmartCare.Model.HelpList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface HelpListRepo extends JpaRepository<HelpList,Integer> {
    List<HelpList> findByngoId(String id);
    @Query(value = "select * from help_list where customer_id=:id",nativeQuery = true)
    List<Map<String,Object>> findRequestByUserId(String id);
}
