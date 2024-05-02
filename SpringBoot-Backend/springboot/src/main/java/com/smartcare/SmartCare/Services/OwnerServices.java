package com.smartcare.SmartCare.Services;

import com.smartcare.SmartCare.DTO.OwnerDTO;
import com.smartcare.SmartCare.Model.Agent;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

public interface OwnerServices {
    Object saveOwner(OwnerDTO ownerDTO);
    Boolean saveAadharCardToLocalStorage(MultipartFile file,String ngoId) throws IOException;
    Object viewOwner(String userId);
    String deleteOwner(String userId);
    String checkNgoId(String email);

    List<Map<String,Object>> listAllAgentByOwnerId(String id);

    Object findNgo(String id);

    int totalNgoMembers(String ownerId);

    List<Map<String,Object>> findAllActiveMembers(String ngoId);
    Resource viewAadharCard(String ngoId) throws MalformedURLException, FileNotFoundException;
}
