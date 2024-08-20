package org.zerock.datie_boot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userno;

    private String id;
    private String pw;
    private String name;
    private String idnumber;
    private String hp;
    private String email;
    private String addr1;
    private String addr;
    private String sex;
    private int age;
    private int acountno;
    private int cardno;
    private String profileReal;
    private String profileOrg;
    private int status;
    private Timestamp moddate;

    // Getters and setters
}
