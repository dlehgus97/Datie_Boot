package org.zerock.datie_boot.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userno;     //고객번호

    private String id;     //아이디
    private String pw;     //비밀번호
    private String name;     //이름
    private String idnumber;     //주민등록번호
    private String hp;     //전화번호
    private String email;     //이메일
    private String addr1;     //주소1
    private String addr2;     //주소2
    private String sex;     //성별
    private int age;     //나이

    private int accountno;     //계좌고우번호
    private int cardno;     //소유카드번호

    private String profile_real;     //프로필실제
    private String profile_org;     //프로필원본
    private int status;     //상태

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime moddate; //수정날짜
}
