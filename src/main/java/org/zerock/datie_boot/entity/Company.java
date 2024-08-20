package org.zerock.datie_boot.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int companyno; // 업체번호
    private String companyname; // 업체명
    private String type;
    private String companyaddress;
}
