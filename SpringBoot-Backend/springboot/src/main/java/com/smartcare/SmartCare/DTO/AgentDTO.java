package com.smartcare.SmartCare.DTO;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AgentDTO {
    private String agentId;
    private String ownerId;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String password;
}
