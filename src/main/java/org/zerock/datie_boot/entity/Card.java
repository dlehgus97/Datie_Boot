package org.zerock.datie_boot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private String serial_number;
}