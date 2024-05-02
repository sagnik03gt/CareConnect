package com.smartcare.SmartCare.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AgentLogInHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date dateTime;
    private String AgentId;
    private String NgoId;
}
