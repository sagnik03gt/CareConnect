package com.smartcare.SmartCare.Controllers;

import com.smartcare.SmartCare.DTO.NgoWithKms;
import com.smartcare.SmartCare.Services.Implementation.RequestServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/SmartCare/request")
public class RequestControllers {
    @Autowired
    private RequestServicesImpl requestServices;
    @GetMapping("/nearestNgo/{lon}/{lat}")
    public ResponseEntity<List<NgoWithKms>> getNgo(@PathVariable double lon,
                                                   @PathVariable double lat){
        return new ResponseEntity<>(requestServices.sentNearestNgoWithDistance(lon,lat), HttpStatus.FOUND);
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
}
