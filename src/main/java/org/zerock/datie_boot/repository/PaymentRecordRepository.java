package org.zerock.datie_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.zerock.datie_boot.entity.PaymentRecord;

public interface PaymentRecordRepository extends JpaRepository
        <PaymentRecord, Long>{
}
