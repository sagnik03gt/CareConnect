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
@RedisHash("redisHelpList")
public class RedisHelpList implements Serializable {
    private Date requestDate;
    private Date solvedTime;
    private String longitude;
    private String latitude;
    private String status;
    private String customerId;
    private String ngoId;
}
