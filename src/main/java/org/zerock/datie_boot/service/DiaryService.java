package org.zerock.datie_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.datie_boot.dto.DiaryDTO;
import org.zerock.datie_boot.entity.Diary;
import org.zerock.datie_boot.repository.DiaryDateRepository;
import org.zerock.datie_boot.repository.DiaryRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

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

    public Diary updateDiary(int diaryNo, int rate, String review, MultipartFile[] images) throws IOException {
        Optional<Diary> optionalDiary = diaryRepository.findById(diaryNo);
        if (optionalDiary.isPresent()) {
            Diary diary = optionalDiary.get();
            diary.setRate(rate);
            diary.setReview(review);

            // 파일 처리
            if (images != null && images.length > 0) {
                StringBuilder uploadReal = new StringBuilder();
                StringBuilder uploadOrg = new StringBuilder();

                for (MultipartFile image : images) {
                    if (!image.isEmpty()) {
                        // 파일 저장 경로 설정
                        String uploadDir = "D:/shpj2/upload/";
                        Path uploadPath = Paths.get(uploadDir);

                        // 디렉토리가 없을 경우 생성
                        if (!Files.exists(uploadPath)) {
                            Files.createDirectories(uploadPath);
                        }

                        // 원본 파일 이름과 저장 파일 이름 설정
                        String originalFilename = image.getOriginalFilename();
                        String storedFilename = System.currentTimeMillis() + "_" + originalFilename;

                        // 파일 저장
                        Path filePath = uploadPath.resolve(storedFilename);
                        Files.copy(image.getInputStream(), filePath);

                        // 파일 정보 저장
                        uploadReal.append(storedFilename).append(",");
                        uploadOrg.append(originalFilename).append(",");
                    }
                }

                // 마지막 쉼표 제거
                diary.setUploadReal(uploadReal.length() > 0 ? uploadReal.substring(0, uploadReal.length() - 1) : null);
                diary.setUploadOrg(uploadOrg.length() > 0 ? uploadOrg.substring(0, uploadOrg.length() - 1) : null);
            }

            return diaryRepository.save(diary);
        }
        throw new RuntimeException("Diary not found with diaryNo: " + diaryNo);
    }
}

