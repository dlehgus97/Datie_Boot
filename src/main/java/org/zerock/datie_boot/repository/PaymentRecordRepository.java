package org.zerock.datie_boot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.zerock.datie_boot.entity.PaymentRecord;

import java.sql.Timestamp;
import java.util.List;

public interface PaymentRecordRepository extends JpaRepository
        <PaymentRecord, Long>{
    Page<PaymentRecord> findByCardno(int cardno, Pageable pageable);
    List<PaymentRecord> findAllByCardno(int cardno);
    List<PaymentRecord> findByCardnoAndConfirmdateBetween(int cardno, Timestamp startDate, Timestamp endDate);
}
