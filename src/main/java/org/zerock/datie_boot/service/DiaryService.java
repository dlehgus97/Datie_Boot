package org.zerock.datie_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.datie_boot.dto.DiaryDTO;
import org.zerock.datie_boot.repository.DiaryDateRepository;
import org.zerock.datie_boot.repository.DiaryRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
public class DiaryService {

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private DiaryDateRepository diaryDateRepository;

    public List<DiaryDTO> getDiarySummaries(Integer userNo, String confirmDate) {
        return diaryRepository.findDiarySummaries(userNo, confirmDate);
    }

    public List<Timestamp> getConfirmDatesByUserNo(int userno) {
        return diaryDateRepository.findConfirmDatesByUserNo(userno);
    }
}

