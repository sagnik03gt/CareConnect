package com.smartcare.SmartCare.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Owner {
    @Id
    private String ownerId;
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
    private Date createdAt;

}
