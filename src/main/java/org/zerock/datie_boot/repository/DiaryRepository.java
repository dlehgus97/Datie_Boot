package org.zerock.datie_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.zerock.datie_boot.entity.Diary;

import java.util.List;


import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Integer> {

    @Query("SELECT d.diaryno, d.review, d.rate, d.uploadOrg, d.uploadReal, p.amount, c.companyname, c.type, c.companyaddress " +
            "FROM Diary d " +
            "JOIN PaymentRecord p ON d.paymentRecord.payno = p.payno " +
            "JOIN Company c ON p.company.companyno = c.companyno " +
            "JOIN Card cd ON p.card.cardno = cd.cardno " +
            "WHERE (cd.userno = :userno OR cd.userno2 = :userno) " +
            "AND FUNCTION('DATE_FORMAT', p.confirmdate, '%Y-%m-%d') = :confirmDate")
    List<Object[]> findDiaryDetailsByUsernoAndConfirmDate(@Param("userno") int userno, @Param("confirmDate") String confirmDate);
}
