package org.zerock.datie_boot.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "paymentkey") // 또는 @Table(name = "paymentKey")
public class PaymentKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyValue;
    private boolean used;

    // 기본 생성자
    public PaymentKey() {
    }

    // 생성자
    public PaymentKey(String keyValue, boolean used) {
        this.keyValue = keyValue;
        this.used = used;
    }

}
