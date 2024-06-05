package com.smartcare.SmartCare.Controllers;

import com.smartcare.SmartCare.DTO.NgoWithKms;
import com.smartcare.SmartCare.Services.Implementation.RequestServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/SmartCare/request")
public class RequestControllers {
    @Autowired
    private RequestServicesImpl requestServices;
    @GetMapping("/nearestNgo/{lon}/{lat}")
    public ResponseEntity<Object> getNgo(@PathVariable double lon,
                                                   @PathVariable double lat) {
        try {
            return new ResponseEntity<>(requestServices.sentNearestNgoWithDistance(lon,lat), HttpStatus.FOUND);
        } catch (Exception ignored) {
            return new ResponseEntity<>("No Nearest Ngo Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/book-ngo/{ngoId}/{custId}/{lon}/{lat}")
    public ResponseEntity<String> bookNgo(@PathVariable String ngoId,
                                          @PathVariable String custId,
                                          @PathVariable String lon,
                                          @PathVariable String lat){
        return new ResponseEntity<>(requestServices.bookedNgo(ngoId,custId,lon,lat),HttpStatus.ACCEPTED);
    }
    @GetMapping("/view/{id}")
    public ResponseEntity<Object> viewRequest(@PathVariable String id){
        return new ResponseEntity<>(requestServices.viewRequestByCustId(id),HttpStatus.FOUND);
    }

    @PostMapping("/done/{id}")
    public ResponseEntity<Object> completeRequest(@PathVariable String id){
        return new ResponseEntity<>(requestServices.markedRequestAsClosed(id),HttpStatus.OK);
    }

    @GetMapping("/all/{type}/{id}")
    public ResponseEntity<Object> allRequest(@PathVariable String type,
                                             @PathVariable String id){
        return new ResponseEntity<>(requestServices.allRequest(type,id),HttpStatus.FOUND);
    }

    @GetMapping("/req-from-redis/{ngoid}")
    public ResponseEntity<Object> requestFromRedis(@PathVariable String ngoid){
        return new ResponseEntity<>(requestServices.gettingReqFromRedisForNgo(ngoid),HttpStatus.FOUND);
    }

    @PostMapping("/sentReqToActiveAgent/{custId}/{ngoId}")
    public ResponseEntity<Object> sentReqToActiveAgent(@PathVariable String custId,@PathVariable String ngoId){
        return new ResponseEntity<>(requestServices.sentReqToActiveAgent(custId,ngoId),HttpStatus.OK);
    }
    @PostMapping("/accept/{agentId}")
    public ResponseEntity<Object> acceptRequest(@PathVariable String agentId){
        return new ResponseEntity<>(requestServices.acceptReq(agentId),HttpStatus.OK);
    }
}
