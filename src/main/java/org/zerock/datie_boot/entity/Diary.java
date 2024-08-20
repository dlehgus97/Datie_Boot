package org.zerock.datie_boot.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "DIARY")
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int diaryno;

    private int payno;
    private String review;
    private Double rate;
    private String uploadReal;
    private String uploadOrg;

    @ManyToOne
    @JoinColumn(name = "payno", insertable = false, updatable = false)
    private PaymentRecord paymentRecord;
}