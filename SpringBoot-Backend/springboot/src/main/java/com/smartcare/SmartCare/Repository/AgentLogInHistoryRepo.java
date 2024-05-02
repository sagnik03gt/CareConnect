package com.smartcare.SmartCare.Repository;

import com.smartcare.SmartCare.Model.AgentLogInHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentLogInHistoryRepo extends JpaRepository<AgentLogInHistory,Integer> {
}
