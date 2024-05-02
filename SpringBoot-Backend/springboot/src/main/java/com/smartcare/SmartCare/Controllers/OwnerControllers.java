package com.smartcare.SmartCare.Controllers;

import com.smartcare.SmartCare.DTO.CustomerDTO;
import com.smartcare.SmartCare.DTO.CustomerDTOUpdate;
import com.smartcare.SmartCare.DTO.OwnerDTO;
import com.smartcare.SmartCare.Response.MappingResponse;
import com.smartcare.SmartCare.Services.Implementation.CustomerServicesImpl;
import com.smartcare.SmartCare.Services.Implementation.OwnerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.resps.GeoRadiusResponse;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/SmartCare/Owner")
public class OwnerControllers {
    @Autowired
    private OwnerServiceImpl ownerService;
    private List<Object> response = new ArrayList<>();
    @PostMapping("/save")
    public ResponseEntity<Object> saveOwner(@RequestBody OwnerDTO ownerDTO){
        try{
            response.clear();
            response.add(ownerService.saveOwner(ownerDTO));
            return new ResponseEntity<>(MappingResponse.mapUniversalResponse("okay",response), HttpStatus.CREATED);
        }
        catch (Exception e){
            response.clear();
            response.add("null");
            e.printStackTrace();
            return new ResponseEntity<>(MappingResponse.mapUniversalResponse("Error while onboarding new ngo owner",response), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/upload-adhar-Card")
    public ResponseEntity<Object> UploadFile(@RequestParam("file") MultipartFile file,@RequestParam("ngoId") String ngoId){
        try{
            response.clear();
            response.add(ownerService.saveAadharCardToLocalStorage(file,ngoId));
            return new ResponseEntity<>(MappingResponse.mapUniversalResponse("okay",response), HttpStatus.CREATED);
        }
        catch (Exception e){
            response.clear();
            response.add("null");
            e.printStackTrace();
            return new ResponseEntity<>(MappingResponse.mapUniversalResponse("Error while uploading ngo owner aadhar card",response), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/get-adhar-Card/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws MalformedURLException, FileNotFoundException {
            return new ResponseEntity<>(ownerService.viewAadharCard(filename), HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> viewOwner(@PathVariable String id){
        try{
            response.clear();
            response.add(ownerService.viewOwner(id));
            return new ResponseEntity<>(MappingResponse.mapUniversalResponse("okay",response), HttpStatus.CREATED);
        }
        catch (Exception e){
            response.clear();
            response.add("No Data Found");
            e.printStackTrace();
            return new ResponseEntity<>(MappingResponse.mapUniversalResponse("Error while getting information about ngo-owner",response), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOwner(@PathVariable String id){
        try{
            response.clear();
            response.add(ownerService.deleteOwner(id));
            return new ResponseEntity<>(MappingResponse.mapUniversalResponse("okay",response), HttpStatus.CREATED);
        }
        catch (Exception e){
            response.clear();
            response.add("nothing shown here");
            e.printStackTrace();
            return new ResponseEntity<>(MappingResponse.mapUniversalResponse("Error while deleting ngo-owner",response), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/checkNgoId/{ngoId}")
    public ResponseEntity<Object> checkNgoId(@PathVariable String ngoId){
        return new ResponseEntity<>(ownerService.checkNgoId(ngoId),HttpStatus.OK);
    }
    @GetMapping("/all/{id}")
    public ResponseEntity<Object> findAllAgent(@PathVariable String id){
        return new ResponseEntity<>(ownerService.listAllAgentByOwnerId(id),HttpStatus.OK);
    }


    @GetMapping("/ngo/{id}")
    public ResponseEntity<Object> findNgo(@PathVariable String id){
        return new ResponseEntity<>(ownerService.findNgo(id),HttpStatus.FOUND);
    }

    @GetMapping("/total-members/@{ownerId}")
    public ResponseEntity<Integer> getTotalMembers(@PathVariable String ownerId){
       return new ResponseEntity<>(ownerService.totalNgoMembers(ownerId),HttpStatus.OK);
    }

    @GetMapping("/active-members/@{ngoId}")
    public ResponseEntity<List<Map<String,Object>>> activeMembers(@PathVariable String ngoId){
        return new ResponseEntity<>(ownerService.findAllActiveMembers(ngoId),HttpStatus.FOUND);
    }
}
