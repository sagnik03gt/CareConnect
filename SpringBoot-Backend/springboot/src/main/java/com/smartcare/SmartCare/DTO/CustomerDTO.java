package com.smartcare.SmartCare.DTO;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private String username;
    private String address;
    private String dob;
    private String phoneNumber;
    private String email;
    private String password;
}
