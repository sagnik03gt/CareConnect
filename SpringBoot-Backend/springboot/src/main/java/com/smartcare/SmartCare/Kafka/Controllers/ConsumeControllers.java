package com.smartcare.SmartCare.Kafka.Controllers;

import com.smartcare.SmartCare.Kafka.Config.AppConstants;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/SmartCare/ConsumeRequest")
public class ConsumeControllers {
    String userDetailsNgo = null;

    @KafkaListener(topics = AppConstants.RequestTopicName,groupId = AppConstants.GROUP_ID)
    public void getLocation(String value){
        this.userDetailsNgo = value;
    }

    @GetMapping
    public Object getRequest(){
        return userDetailsNgo;
    }

}
