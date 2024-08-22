package org.zerock.datie_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.zerock.datie_boot.entity.PaymentRecord;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface DiaryDateRepository extends JpaRepository<PaymentRecord, Integer> {
    @Query("SELECT p.confirmdate FROM PaymentRecord p JOIN Card c ON p.cardno = c.cardno WHERE c.userno = :userno OR c.userno2 = :userno")
    List<Timestamp> findConfirmDatesByUserNo(int userno);

}
