package com.smartcare.SmartCare.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Agent {
    @Id
    private String agentId;
    private String name;
    private String PhoneNumber;
    private String email;
    private String address;
    private Date createdAt;
    private String password;
    @ManyToOne
    @JoinColumn(name = "ownerId")
    private Owner owner;


}
