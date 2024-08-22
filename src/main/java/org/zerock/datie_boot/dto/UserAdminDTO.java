package org.zerock.datie_boot.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAdminDTO {
    private int id;
    private String userId;
    private String pw;
    private String name;
    private String idnumber;
    private String hp;
    private String email;
    private String addr1;
    private String addr2;
    private int sex;
    private int age;
    private int cardno;
    private String profileReal;
    private String profileOrg;
    private int status;
    private LocalDateTime moddate;

    private int cStatus;

    private String searchWord;
    private String searchType;
}
