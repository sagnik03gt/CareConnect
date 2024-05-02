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
@RedisHash("Customer")
public class RedisCustomer implements Serializable {
    private String userId;
    private String Username;
    private String address;
    private String dob;
    private String email;
    private Date createdAt;
    private String phoneNumber;
    private String password;
}
