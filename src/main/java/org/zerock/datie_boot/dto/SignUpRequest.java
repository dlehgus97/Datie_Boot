package org.zerock.datie_boot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String id;    // 아이디 추가
    private String name;   // 사용자 이름
    private String pw;    // 비밀번호
    private String addr1;     // 주소
    private String addr2; // 상세 주소
    private String bank;
    private int accountno;      // 계좌
    private Integer hp;
    private String email;
//    private String idnumber;
    private String sex;
    private Integer age;

    // 기본 생성자
    public SignUpRequest() {
    }

    // 생성자
    public SignUpRequest(String id, String name, String pw, String addr1, String addr2, Integer hp, String email, int accountno, String idnumber, String sex, Integer age, String bank ) {
        this.id = id;
        this.name = name;
        this.pw = pw;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.hp = hp;
        this.email = email;
        this.accountno = accountno;
//        this.idnumber = idnumber;
        this.sex=sex;
        this.age=age;
        this.bank=bank;


    }

}