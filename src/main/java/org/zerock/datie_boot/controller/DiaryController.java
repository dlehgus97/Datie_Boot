package org.zerock.datie_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.datie_boot.dto.DiaryDTO;
import org.zerock.datie_boot.entity.Diary;
import org.zerock.datie_boot.entity.DiaryImage;
import org.zerock.datie_boot.repository.DiaryImageRepository;
import org.zerock.datie_boot.repository.DiaryRepository;
import org.zerock.datie_boot.service.DiaryImageService;
import org.zerock.datie_boot.service.DiaryService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:3000/","http://localhost/"})
@RestController
@RequestMapping("/api/diary")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private DiaryImageService diaryImageService;

    @Autowired
    private DiaryImageRepository diaryImageRepository;

    @GetMapping("/detail")
    public ResponseEntity<List<DiaryDTO>> result(@RequestParam("userNo") int userNo,
                                                            @RequestParam("confirmDate") String confirmDate) {
        System.out.println("Received userNo: " + userNo + ", confirmDate: " + confirmDate);
        List<DiaryDTO> results = diaryRepository.findDiarySummaries(userNo, confirmDate);
        System.out.println(results);
        // 만약 데이터가 없을 경우, 204 No Content 상태 코드 반환
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(results); // HTTP 200 상태 코드와 함께 JSON 형식으로 데이터 반환
    }

    @GetMapping("/confirmdate")
    public List<Timestamp> getConfirmDatesByUserNo(@RequestParam int userno) {
        return diaryService.getConfirmDatesByUserNo(userno);
    }

    @PostMapping("/upload")
    public ResponseEntity<Diary> updateDiary(
            @RequestParam int diaryNo,
            @RequestParam int rate,
            @RequestParam String review,
            @RequestParam(required = false) MultipartFile[] images) {
        try {
            // 다이어리 업데이트
            Diary updatedDiary = diaryService.updateDiary(diaryNo, rate, review, images);

            // 이미지가 있는 경우 이미지 업로드 처리
            if (images != null && images.length > 0) {
                diaryImageService.saveImages(diaryNo, Arrays.asList(images));
            }

            return ResponseEntity.ok(updatedDiary);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/imageUpload")
    public ResponseEntity<String> uploadImages(
            @RequestParam("diaryNo") int diaryNo,
            @RequestParam("images") List<MultipartFile> images) {
        try {
            // 다이어리 이미지 저장
            diaryImageService.saveImages(diaryNo, images);
            return new ResponseEntity<>("Images uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload images", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("image/{diaryNo}")
//    public ResponseEntity<List<Resource>> getDiaryImages(@PathVariable int diaryNo) {
//        List<DiaryImage> images = diaryImageService.getImagesByDiaryNo(diaryNo);
//
//        List<Resource> resources = images.stream().map(image -> {
//            try {
//                Path filePath = Paths.get("src/main/resources/static/upload/" + image.getImageName());
//                return new UrlResource(filePath.toUri());
//            } catch (Exception e) {
//                throw new RuntimeException("File not found: " + image.getImageName());
//            }
//        }).collect(Collectors.toList()); // List<UrlResource> -> List<Resource>
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"images.zip\"")
//                .body(resources);
//    }

    @GetMapping("images/{diaryNo}")
    public ResponseEntity<List<String>> getDiaryImageUrls(@PathVariable int diaryNo) {
        List<DiaryImage> images = diaryImageService.getImagesByDiaryNo(diaryNo);

        // URL 리스트 반환
        List<String> imageUrls = images.stream()
                .map(image -> "http://localhost:8090/api/diary/image/" + image.getImageName())
                .collect(Collectors.toList());

        return ResponseEntity.ok(imageUrls);
    }

    @GetMapping("image/{imageName}")
    public ResponseEntity<Resource> getDiaryImage(@PathVariable String imageName) {
        try {
            Path filePath = Paths.get("src/main/resources/static/upload/" + imageName);
            Resource resource = new UrlResource(filePath.toUri());
            System.out.println(resource.getFilename());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException("File not found: " + imageName);
        }
    }


}
