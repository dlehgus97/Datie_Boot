package org.zerock.datie_boot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "PAYMENTRECORD")
public class PaymentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int payno;

    private int cardno;
    private int companyno;
    private int amount;
    private int peramount;
    private int bonus;
    @Lob
    private String content;
    private Timestamp confirmdate;
    private int paystate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cardno", insertable = false, updatable = false)
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyno", insertable = false, updatable = false)
    private Company company;
}