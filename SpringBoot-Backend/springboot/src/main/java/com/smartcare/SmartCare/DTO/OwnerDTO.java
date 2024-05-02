package com.smartcare.SmartCare.DTO;


import jakarta.annotation.sql.DataSourceDefinitions;
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
    private String phoneNumber;
    private String ngoId;
    private String ngoAddress;
    private String longitude;
    private String latitude;
    private String password;
}
