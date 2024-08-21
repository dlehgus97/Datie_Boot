package org.zerock.datie_boot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "accounttran")
public class AccountTran {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accounttranId;

    private int accountno;
    private int amount;
    private String name;
    private Timestamp confirmdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acountno", insertable = false, updatable = false)
    private Account account;

    // Getters and setters
}
