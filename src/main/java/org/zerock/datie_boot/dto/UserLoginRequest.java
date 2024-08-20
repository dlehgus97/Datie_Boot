package org.zerock.datie_boot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequest {
    private String id;
    private String pw;
}

