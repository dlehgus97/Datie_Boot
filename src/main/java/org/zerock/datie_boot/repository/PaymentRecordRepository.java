package org.zerock.datie_boot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.datie_boot.entity.PaymentRecord;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface PaymentRecordRepository extends JpaRepository
        <PaymentRecord, Long>{
    Page<PaymentRecord> findByCardno(int cardno, Pageable pageable);
    List<PaymentRecord> findAllByCardno(int cardno);


    @Query(
            "SELECT COUNT(c.userno) " +
                    "FROM PaymentRecord pr " +
                    "JOIN pr.card c " +
                    "WHERE DATE(pr.confirmdate) = :today " +
                    "GROUP BY c.userno"
    )
    String adminGetToday(Date today);



    @Query(
            "SELECT p.category, COUNT(p) " +
                    "FROM PaymentRecord p " +
                    "WHERE MONTH(p.confirmdate) = MONTH(CURRENT_DATE) " +
                    "AND YEAR(p.confirmdate) = YEAR(CURRENT_DATE) " +
                    "GROUP BY p.category"
    )
    List<Object[]> adminGetCategory();


    @Query(
            "SELECT \n" +
                    "    CASE \n" +
                    "        WHEN u.age BETWEEN 10 AND 19 THEN '10대'\n" +
                    "        WHEN u.age BETWEEN 20 AND 29 THEN '20대'\n" +
                    "        WHEN u.age BETWEEN 30 AND 39 THEN '30대'\n" +
                    "        ELSE '기타'\n" +
                    "    END AS ageGroup,\n" +
                    "    \n" +
                    "    COUNT(CASE WHEN pr.category = '외식' THEN pr.category ELSE NULL END) AS 외식,\n" +
                    "    COUNT(CASE WHEN pr.category = '미용/패션' THEN pr.category ELSE NULL END) AS 미용패션, \n" +
                    "    COUNT(CASE WHEN pr.category = '문화/여가' THEN pr.category ELSE NULL END) AS 문화여가,\n" +
                    "    COUNT(CASE WHEN pr.category = '식료품' THEN pr.category ELSE NULL END) AS 식료품,\n" +
                    "    COUNT(CASE WHEN pr.category = '기타' THEN pr.category ELSE NULL END) AS 기타\n" +
                    "    \n" +
                    "FROM \n" +
                    "    PaymentRecord pr \n" +
                    "JOIN \n" +
                    "    pr.card c \n" +
                    "JOIN \n" +
                    "    User u ON c.userno = u.userno \n" +
                    "WHERE \n" +
                    "    FUNCTION('MONTH', pr.confirmdate) = FUNCTION('MONTH', CURRENT_DATE()) AND \n" +
                    "    FUNCTION('YEAR', pr.confirmdate) = FUNCTION('YEAR', CURRENT_DATE())\n" +
                    "GROUP BY \n" +
                    "    ageGroup"
    )
    List<Object[]> adminGetAge();



    @Query(
            "SELECT " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 1 THEN 1 ELSE 0 END) AS I1, " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 2 THEN 1 ELSE 0 END) AS I2, " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 3 THEN 1 ELSE 0 END) AS I3, " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 4 THEN 1 ELSE 0 END) AS I4, " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 5 THEN 1 ELSE 0 END) AS I5, " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 6 THEN 1 ELSE 0 END) AS I6, " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 7 THEN 1 ELSE 0 END) AS I7, " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 8 THEN 1 ELSE 0 END) AS I8, " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 9 THEN 1 ELSE 0 END) AS I9, " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 10 THEN 1 ELSE 0 END) AS I10, " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 11 THEN 1 ELSE 0 END) AS I11, " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 12 THEN 1 ELSE 0 END) AS I12, " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 13 THEN 1 ELSE 0 END) AS I13, " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 14 THEN 1 ELSE 0 END) AS I14, " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 15 THEN 1 ELSE 0 END) AS I15, " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 16 THEN 1 ELSE 0 END) AS I16, " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 17 THEN 1 ELSE 0 END) AS I17, " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 18 THEN 1 ELSE 0 END) AS I18, " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 19 THEN 1 ELSE 0 END) AS I19, " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 20 THEN 1 ELSE 0 END) AS I20, " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 21 THEN 1 ELSE 0 END) AS I21, " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 22 THEN 1 ELSE 0 END) AS I22, " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 23 THEN 1 ELSE 0 END) AS I23, " +
                    "SUM(CASE WHEN FUNCTION('HOUR', pr.confirmdate) = 0 THEN 1 ELSE 0 END) AS I24 " +
                    "FROM PaymentRecord pr " +
                    "WHERE MONTH(pr.confirmdate) = MONTH(CURRENT_DATE) " +
                    "AND YEAR(pr.confirmdate) = YEAR(CURRENT_DATE)"
    )
    List<Object[]> adminGetTime();
    List<PaymentRecord> findByCardnoAndConfirmdateBetween(int cardno, Timestamp startDate, Timestamp endDate);

}
