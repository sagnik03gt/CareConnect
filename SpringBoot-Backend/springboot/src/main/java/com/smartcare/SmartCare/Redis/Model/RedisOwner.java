package com.smartcare.SmartCare.Redis.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Owner")
public class RedisOwner implements Serializable {
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
