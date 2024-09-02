package org.zerock.datie_boot.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userno;

    private String id;
    private String pw;
    private String name;
//    private String idnumber;
    private String hp;
    private String email;
    private String addr1;
    private String addr2;
    private String sex;
    private int age;
    private String bank;
    private int accountno;
    private int cardno;
    private String profileReal;
    private String profileOrg;
    private int status;
    private String role;
  
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime moddate; //수정날짜
}
