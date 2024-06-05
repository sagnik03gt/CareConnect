package com.smartcare.SmartCare.Kafka.Controllers;

import com.smartcare.SmartCare.Kafka.Config.AppConstants;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static io.lettuce.core.pubsub.PubSubOutput.Type.message;

@RestController
@RequestMapping("/SmartCare/ConsumeRequest")
public class ConsumeControllers {
    String detailsKey = null;
    String userId = null;
    String ngoId = null;

    @KafkaListener(topics = AppConstants.RequestTopicName,groupId = AppConstants.GROUP_ID)
    public void getDetails( String message) {
        userId = message.substring(8,message.indexOf(','));
        ngoId = message.substring(23,message.length()-1);
    }

    @GetMapping("/{id}")
    public Object getRequest(@PathVariable String id){
        if(id.equals(ngoId)){
            return userId;
        }
        return "no request has arrived";
    }

}
