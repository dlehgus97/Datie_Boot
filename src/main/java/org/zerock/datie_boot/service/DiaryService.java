package org.zerock.datie_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.datie_boot.repository.DiaryRepository;

import java.util.List;

@Service
public class DiaryService {

    @Autowired
    private DiaryRepository diaryRepository;

    public List<Object[]> getDiaryDetails(int userno, String confirmDate) {
        return diaryRepository.findDiaryDetailsByUsernoAndConfirmDate(userno, confirmDate);
    }
}
