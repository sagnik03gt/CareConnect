package com.smartcare.SmartCare.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.UnifiedJedis;

@Configuration
public class JedisConfig {
    @Bean
    public UnifiedJedis jedis(){
        return new UnifiedJedis("redis://localhost:7778");
    }
}
