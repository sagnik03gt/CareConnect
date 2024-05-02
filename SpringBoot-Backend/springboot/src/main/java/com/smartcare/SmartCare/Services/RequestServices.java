package com.smartcare.SmartCare.Services;

import com.smartcare.SmartCare.DTO.NgoWithKms;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public interface RequestServices {
    List<String> findNearestNgoByLongLat(double longitude,double latitude);
    double calculateDistanceBetweenTwoLongLat(double long1,double lat1,double long2,double lat2);

    List<NgoWithKms> sentNearestNgoWithDistance(double longitude, double latitude);

    String bookedNgo(String ngoId,String custId,String lon,String lat);

    Object viewRequestByCustId(String id);

    Object markedRequestAsClosed(String id);
    Object allRequest(String type,String id);
}
