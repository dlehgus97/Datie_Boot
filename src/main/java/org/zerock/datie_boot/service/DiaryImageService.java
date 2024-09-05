package org.zerock.datie_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.datie_boot.entity.DiaryImage;
import org.zerock.datie_boot.repository.DiaryImageRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DiaryImageService {

    @Value("${upload.dir}")
    private String dir;



    @Autowired
    private DiaryImageRepository diaryImageRepository;

    public void saveImages(int diaryNo, List<MultipartFile> images) throws IOException {
        for (MultipartFile image : images) {
            String originalFilename = image.getOriginalFilename();
            String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;
            Path filePath = Paths.get(dir, uniqueFilename);

            // 파일을 로컬 디렉토리에 저장
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, image.getBytes());

            // DB에 이미지 정보 저장
            DiaryImage diaryImage = new DiaryImage();
            diaryImage.setDiaryno(diaryNo);
            diaryImage.setImageName(uniqueFilename);
            diaryImageRepository.save(diaryImage);
        }
    }

    public List<DiaryImage> getImagesByDiaryNo(int diaryno) {
        return diaryImageRepository.findByDiaryno(diaryno);
    }
}
