package com.smartcare.SmartCare.Controllers;

import com.smartcare.SmartCare.DTO.AgentDTO;
import com.smartcare.SmartCare.DTO.OwnerDTO;
import com.smartcare.SmartCare.Response.MappingResponse;
import com.smartcare.SmartCare.Services.AgentServices;
import com.smartcare.SmartCare.Services.Implementation.AgentServicesImpl;
import com.smartcare.SmartCare.Services.Implementation.OwnerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/SmartCare/agent")
public class AgentControllers {
    @Autowired
    private AgentServicesImpl agentServices;
    private List<Object> response = new ArrayList<>();

    @PostMapping("/save")
    public ResponseEntity<Object> saveAgent(@RequestBody AgentDTO agentDTO){
        try{
            response.clear();
            response.add(agentServices.saveAgent(agentDTO));
            return new ResponseEntity<>(MappingResponse.mapUniversalResponse("okay",response), HttpStatus.CREATED);
        }
        catch (Exception e){
            response.clear();
            response.add("null");
            e.printStackTrace();
            return new ResponseEntity<>(MappingResponse.mapUniversalResponse("Error while on-boarding new ngo agent",response), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> viewAgent(@PathVariable String id){
        try{
            response.clear();
            response.add(agentServices.viewAgent(id));
            return new ResponseEntity<>(MappingResponse.mapUniversalResponse("okay",response), HttpStatus.CREATED);
        }
        catch (Exception e){
            response.clear();
            response.add("No Data Found");
            e.printStackTrace();
            return new ResponseEntity<>(MappingResponse.mapUniversalResponse("Error while getting information about ngo-owner",response), HttpStatus.BAD_REQUEST);
        }
    }

    //just for ngo owner only
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAgent(@PathVariable String id){
        try{
            response.clear();
            response.add(agentServices.deleteAgent(id));
            return new ResponseEntity<>(MappingResponse.mapUniversalResponse("okay",response), HttpStatus.CREATED);
        }
        catch (Exception e){
            response.clear();
            response.add("nothing shown here");
            e.printStackTrace();
            return new ResponseEntity<>(MappingResponse.mapUniversalResponse("Error while deleting ngo-owner",response), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/nextId/{id}")
    public ResponseEntity<Object> getNextId(@PathVariable String id){
        return new ResponseEntity<>(agentServices.generateNextAgentId(id),HttpStatus.OK);
    }

    @PostMapping("/login/{id}/{pass}/{ngoId}")
    public ResponseEntity<Object> login(@PathVariable String id,@PathVariable String pass,@PathVariable String ngoId){
        return new ResponseEntity<>(agentServices.logIn(id,pass,ngoId),HttpStatus.ACCEPTED);
    }
    @PostMapping("/logout/{id}/{pass}/{ngoId}")
    public ResponseEntity<Object> logout(@PathVariable String id,@PathVariable String pass,@PathVariable String ngoId){
        return new ResponseEntity<>(agentServices.logout(id,pass,ngoId),HttpStatus.ACCEPTED);
    }

}
