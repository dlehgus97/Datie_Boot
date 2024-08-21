package org.zerock.datie_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.datie_boot.entity.Card;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {


    // userno로 Card를 찾는 메서드
    Optional<Card> findByUserno(int userno);

    //carno로 card를 찾는 메서드
    Card findByCardno(int cardno);
}
