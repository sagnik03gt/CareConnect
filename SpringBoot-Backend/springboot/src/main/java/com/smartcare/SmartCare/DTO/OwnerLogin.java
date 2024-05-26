package com.smartcare.SmartCare.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerLogin {
    private String ngoId;
    private String ngoPassword;
}
