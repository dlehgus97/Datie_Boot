package org.zerock.datie_boot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "ACCOUNT")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountno;

    private int userno;
    private String bank;
    private String acount;
    private int balance;
    private int status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userno", insertable = false, updatable = false)
    private User user;
}