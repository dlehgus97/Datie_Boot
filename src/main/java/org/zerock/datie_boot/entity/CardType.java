package org.zerock.datie_boot.entity;

import jakarta.persistence.*;
import lombok.ToString;

@Entity
@Table(name = "cardtype")
@ToString
public class CardType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cardtypeno;

    private String cardname;
    private String carddesign;
}
