package com.smartcare.SmartCare.Repository;

import com.smartcare.SmartCare.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,String> {
    Customer findByuserId(String id);

    Boolean existsByemail(String email);
}
