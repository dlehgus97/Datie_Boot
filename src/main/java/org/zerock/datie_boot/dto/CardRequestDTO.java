package org.zerock.datie_boot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardRequestDTO {
    private String id;
    private String password;

    private int cstatus;
    private String cardpw;
    private int cardtypeno;
    private String initial;
    private int titleHolder;
    private int userno;
    private int usernoLover;
}
