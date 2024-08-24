package org.zerock.datie_boot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryDTO {
    private int diaryNo;
    private String review;
    private Integer rate;
    private String uploadOrg;
    private String uploadReal;
    private String companyName;
    private String type;
    private String companyAddress;

}
