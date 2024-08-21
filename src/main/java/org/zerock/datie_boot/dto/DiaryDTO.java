package org.zerock.datie_boot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDTO {
    private Long diaryNo;
    private String review;
    private Integer rate;
    private String filenameOrg;
    private String filenameReal;
    private Integer amount;
    private String companyName;
    private String type;
    private String companyAddress;
}
