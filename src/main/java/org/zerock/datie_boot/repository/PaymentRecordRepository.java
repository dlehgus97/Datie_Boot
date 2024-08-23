package org.zerock.datie_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.zerock.datie_boot.entity.PaymentRecord;

import java.util.List;

public interface PaymentRecordRepository extends JpaRepository
        <PaymentRecord, Long>{
    List<PaymentRecord> findByCardno(int cardno);
}
