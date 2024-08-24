package org.zerock.datie_boot.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "diary")
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diaryno")
    private int diaryNo;

    @Column(name = "payno")
    private int payNo;

    @Column(name = "review")
    private String review;

    @Column(name = "rate")
    private Integer rate;

    @Column(name = "upload_real")
    private String uploadReal;

    @Column(name = "upload_org")
    private String uploadOrg;

    // Getters and Setters
}