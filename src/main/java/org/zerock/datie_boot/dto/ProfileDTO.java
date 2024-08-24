package org.zerock.datie_boot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    private int userno; // 추가
    private String name;
    private String pw;
    private String email;
    private String addr1;
    private String addr2;
    private String sex;
    private int age;
    private int accountno; // 추가
    private String bank;
    private String account;
    private LocalDateTime moddate;
}
