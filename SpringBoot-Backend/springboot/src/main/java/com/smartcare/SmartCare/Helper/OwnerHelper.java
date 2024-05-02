package com.smartcare.SmartCare.Helper;

import com.smartcare.SmartCare.DTO.OwnerDTO;
import com.smartcare.SmartCare.Model.Owner;
import com.smartcare.SmartCare.Repository.OwnerRepo;

import java.util.Date;
import java.util.UUID;

public class OwnerHelper {
    public static Owner convertIntoOwner(OwnerDTO ownerDTO , Owner owner){
        owner.setOwnerId(UUID.randomUUID().toString().substring(0,7));
        owner.setName(ownerDTO.getName());
        owner.setAddress(ownerDTO.getAddress());
        owner.setCreatedAt(new Date());
        owner.setDob(ownerDTO.getDob());
        owner.setEmail(ownerDTO.getEmail());
        owner.setPhoneNumber(ownerDTO.getPhoneNumber());
        owner.setNgoId(ownerDTO.getNgoId());
        owner.setNgoAddress(ownerDTO.getNgoAddress());
        owner.setLongitude(ownerDTO.getLongitude());
        owner.setLatitude(ownerDTO.getLatitude());
        owner.setPassword(ownerDTO.getPassword());
        return owner;
    }
}
