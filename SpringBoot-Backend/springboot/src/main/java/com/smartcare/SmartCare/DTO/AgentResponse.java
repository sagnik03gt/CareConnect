package com.smartcare.SmartCare.DTO;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AgentResponse {
    private String agentId;
    private String ngoId;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private Date createAt;
}
