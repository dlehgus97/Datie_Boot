package org.zerock.datie_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.datie_boot.dto.DiaryDTO;
import org.zerock.datie_boot.repository.DiaryRepository;
import org.zerock.datie_boot.service.DiaryService;

import java.sql.Timestamp;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000/","http://localhost/"})
@RestController
@RequestMapping("/api/diary")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private DiaryRepository diaryRepository;


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
}
