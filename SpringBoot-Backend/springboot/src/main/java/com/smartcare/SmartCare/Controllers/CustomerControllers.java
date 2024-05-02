package com.smartcare.SmartCare.Controllers;

import com.smartcare.SmartCare.DTO.CustomerDTO;
import com.smartcare.SmartCare.DTO.CustomerDTOUpdate;
import com.smartcare.SmartCare.Response.MappingResponse;
import com.smartcare.SmartCare.Services.Implementation.CustomerServicesImpl;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/SmartCare/Customer")
public class CustomerControllers {
    @Autowired
    private CustomerServicesImpl customerServices;
    private List<Object> response = new ArrayList<>();

    @PostMapping("/save")
    public ResponseEntity<Object>  saveCust(@RequestBody CustomerDTO customerDTO){
        try{
            response.clear();
            response.add(customerServices.saveCust(customerDTO));
            return new ResponseEntity<>(MappingResponse.mapUniversalResponse("okay",response), HttpStatus.CREATED);
        }
        catch (Exception e){
            response.clear();
            response.add("null");
            e.printStackTrace();
            return new ResponseEntity<>(MappingResponse.mapUniversalResponse("Error while onboarding new customer",response), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> viewCust(@PathVariable String id){
        try{
            response.clear();
            response.add(customerServices.viewCust(id));
            return new ResponseEntity<>(MappingResponse.mapUniversalResponse("okay",response), HttpStatus.CREATED);
        }
        catch (Exception e){
            response.clear();
            response.add("No Data Found");
            e.printStackTrace();
            return new ResponseEntity<>(MappingResponse.mapUniversalResponse("Error while getting information about customer",response), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update")
    public ResponseEntity<Object> updateCust(@RequestBody CustomerDTOUpdate customerDTOUpdate){
        try{
            response.clear();
            response.add(customerServices.updateCust(customerDTOUpdate));
            return new ResponseEntity<>(MappingResponse.mapUniversalResponse("okay",response), HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            response.clear();
            response.add("oopss !!! ");
            e.printStackTrace();
            return new ResponseEntity<>(MappingResponse.mapUniversalResponse("Error while updating customer details",response), HttpStatus.NOT_ACCEPTABLE);
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCust(@PathVariable String id){
        try{
            response.clear();
            response.add(customerServices.deleteCust(id));
            return new ResponseEntity<>(MappingResponse.mapUniversalResponse("okay",response), HttpStatus.CREATED);
        }
        catch (Exception e){
            response.clear();
            response.add("nothing shown here");
            e.printStackTrace();
            return new ResponseEntity<>(MappingResponse.mapUniversalResponse("Error while deleting customer",response), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/checkEmail/{email}")
    public ResponseEntity<Object> checkEmail(@PathVariable String email){
        return new ResponseEntity<>(customerServices.checkEmail(email),HttpStatus.OK);
    }
}
