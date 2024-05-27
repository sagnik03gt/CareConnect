package com.smartcare.SmartCare.DTO;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDTO {
    private String name;
    private String address;
    private String dob;
    private String email;
    private String latitude;
    private String longitude;
    private String phoneNumber;
    private String ngoId;
    private String ngoAddress;
    private String password;
}
