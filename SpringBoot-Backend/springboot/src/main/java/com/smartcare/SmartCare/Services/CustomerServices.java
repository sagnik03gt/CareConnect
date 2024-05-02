package com.smartcare.SmartCare.Services;

import com.smartcare.SmartCare.DTO.CustomerDTO;
import com.smartcare.SmartCare.DTO.CustomerDTOUpdate;

public interface CustomerServices {
    Object saveCust(CustomerDTO customerDTO);
    Object updateCust(CustomerDTOUpdate customerDTOUpdate);
    Object viewCust(String userId);
    String deleteCust(String userId);
    String checkEmail(String email);
}
