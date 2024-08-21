package org.zerock.datie_boot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.datie_boot.entity.Card;

public interface CardAdminRepository extends JpaRepository<Card, Integer> {
    @Modifying // update는 반드시 추가
    @Query("update Card set cStatus=3 where userno=:id ")
    public void deactivateByUserId(String id);


    @Modifying // update는 반드시 추가
    @Query("update Card set cStatus=2 where userno=:id ")
    public void holdByUserId(String id);
}
