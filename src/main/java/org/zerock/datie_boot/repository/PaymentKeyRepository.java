package org.zerock.datie_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.datie_boot.entity.PaymentKey;

public interface PaymentKeyRepository extends JpaRepository<PaymentKey, Long> {
    PaymentKey findByKeyValue(String keyValue);
}
