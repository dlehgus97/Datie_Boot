package org.zerock.datie_boot.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountno;

    private int userno;
    private String bank;
    private String account;
    private int balance;
    private int status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userno", insertable = false, updatable = false)
    private User user;
}