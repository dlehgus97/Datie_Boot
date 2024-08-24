package org.zerock.datie_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.zerock.datie_boot.dto.DiaryDTO;
import org.zerock.datie_boot.entity.Diary;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Integer> {

    @Query("SELECT new org.zerock.datie_boot.dto.DiaryDTO(d.diaryNo, d.review, d.rate, d.uploadOrg, d.uploadReal, com.companyname, com.type, com.companyaddress) " +
            "FROM Diary d " +
            "JOIN PaymentRecord p ON d.payNo = p.payno " +
            "JOIN Company com ON p.companyno = com.companyno " +
            "JOIN Card cd ON p.cardno = cd.cardno " +
            "WHERE (cd.userno = :userNo OR cd.userno2 = :userNo) " +
            "AND FUNCTION('DATE_FORMAT', p.confirmdate, '%Y-%m-%d') = :confirmDate ")
    List<DiaryDTO> findDiarySummaries(@Param("userNo") int userNo,
                                      @Param("confirmDate") String confirmDate);


}