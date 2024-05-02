package com.smartcare.SmartCare.Model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
    @Id
    private String userId;
    private String Username;
    @Column(length = 150)
    private String address;
    private String dob;
    private String email;
    private Date createdAt;
    private String phoneNumber;
    @Column(length = 1500)
    private String password;


}
