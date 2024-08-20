package org.zerock.datie_boot.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "COMPANY")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int companyno;

    private String companyname;
    private String type;
    private String companyaddress;

    // Getters and setters
}
