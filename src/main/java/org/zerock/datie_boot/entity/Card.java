package org.zerock.datie_boot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cardno;//카드번호

    private int cardpw;//카드비밇번호

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userno")
    private User userno; // 사용자 1
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userno2")
//    private User userno2; // 사용자 2

    private int c_status;//상태번호
    private int cvc;//cvc

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime c_moddate; //수정날짜

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime date; //유효기간

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cardtypeno")
    private Card card; // 카드 정보
}
