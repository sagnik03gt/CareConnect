package com.smartcare.SmartCare.Helper;

import com.smartcare.SmartCare.DTO.CustomerDTO;
import com.smartcare.SmartCare.DTO.CustomerDTOUpdate;
import com.smartcare.SmartCare.Model.Customer;
import com.smartcare.SmartCare.Repository.CustomerRepo;

import java.util.Date;
import java.util.UUID;

public class CustomerHelper {
    public static Customer convertIntoCustomer(CustomerDTO customerDTO,Customer customer){
        customer.setUserId(UUID.randomUUID().toString().substring(0,7));
        customer.setCreatedAt(new Date());
        customer.setUsername(customerDTO.getUsername());
        customer.setAddress(customerDTO.getAddress());
        customer.setDob(customerDTO.getDob());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setPassword(customerDTO.getPassword());
        return customer;

    }
    public static Customer convertIntoCustomerUpdate(CustomerDTOUpdate customerDTO, Customer customer){
        customer.setUserId(customer.getUserId());
        customer.setCreatedAt(customer.getCreatedAt());
        customer.setUsername(customerDTO.getUsername());
        customer.setAddress(customerDTO.getAddress());
        customer.setDob(customerDTO.getDob());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setPassword(customer.getPassword());
        return customer;

    }
}
