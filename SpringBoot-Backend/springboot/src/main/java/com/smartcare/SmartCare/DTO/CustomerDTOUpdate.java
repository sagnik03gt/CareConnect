package com.smartcare.SmartCare.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerDTOUpdate {
    private String userId;
    private String username;
    private String address;
    private String dob;
    private String email;
    private String phoneNumber;
}
