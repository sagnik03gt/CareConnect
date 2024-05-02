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
@RedisHash("Agent")
public class RedisAgent implements Serializable {
    private String agentId;
    private String ownerId;
    private String name;
    private String PhoneNumber;
    private String email;
    private String address;
    private Date createdAt;
}
