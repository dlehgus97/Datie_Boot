package org.zerock.datie_boot.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class PasswordChangeRequestDTO {
    private int userno;
    private String currentPassword;
    private String newPassword;
}
