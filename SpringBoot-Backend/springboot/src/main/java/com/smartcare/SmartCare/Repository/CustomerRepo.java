package com.smartcare.SmartCare.Repository;

import com.smartcare.SmartCare.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,String> {
    Customer findByuserId(String id);

    Customer findByemail(String email);

    Boolean existsByemail(String email);

    @Query(value = "select password from customer where email=:emailId",nativeQuery = true)
    String findpassword(String emailId);
}
