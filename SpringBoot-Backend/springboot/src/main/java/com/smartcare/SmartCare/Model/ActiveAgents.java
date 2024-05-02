package com.smartcare.SmartCare.Model;

import com.smartcare.SmartCare.Model.Agent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ActiveAgents {
    @Id
    private String agentId;

    private String status;

    private String NgoId;

}
