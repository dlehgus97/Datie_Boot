package org.zerock.datie_boot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cardno;

    private int cardpw;
    private int userno;
    private int userno2;
    private int cStatus;
    private Timestamp cModdate;
    private int cardtypeno;
    private int cvc;
    private Timestamp date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userno", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cardtypeno", insertable = false, updatable = false)
    private CardType cardType;
}