package org.zerock.datie_boot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.datie_boot.entity.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    private User user;
    private String bank;
    private String account;
}
