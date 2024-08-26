package org.zerock.datie_boot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.datie_boot.entity.Card;

import java.sql.Timestamp;

public interface CardAdminRepository extends JpaRepository<Card, Integer> {


    @Modifying // update는 반드시 추가
    @Query("UPDATE Card c SET c.cStatus = 3 WHERE c.userno = :id " +
            "AND c.cModdate = :maxDate")
    public void deactivateByUserId(String id, Timestamp maxDate);


    @Modifying // update는 반드시 추가
    @Query("UPDATE Card c SET c.cStatus = 2 WHERE c.userno = :id " +
            "AND c.cModdate = :maxDate")
    public void holdByUserId(String id, Timestamp maxDate);


    @Modifying // update는 반드시 추가
    @Query("update User set cardno=0 where userno=:id ")
    public void zeroCardno(String id);

    @Query("SELECT MAX(c.cModdate) " +
            "FROM Card c " +
            "WHERE c.userno = :id")
    String getMaxDate(String id);
}
