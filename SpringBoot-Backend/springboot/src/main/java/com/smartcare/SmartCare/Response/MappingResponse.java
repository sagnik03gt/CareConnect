package com.smartcare.SmartCare.Response;

import java.util.List;

public class MappingResponse {

    public static UniversalResponse mapUniversalResponse(String errorStatus, List<Object> response){
        UniversalResponse universalResponse = new UniversalResponse();

        universalResponse.setMessage(errorStatus);
        universalResponse.setResponse(response);
        return universalResponse;
    }
}
