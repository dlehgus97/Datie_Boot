package org.zerock.datie_boot.controller;

import com.nimbusds.openid.connect.sdk.UserInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.zerock.datie_boot.dto.CardRequestDTO;
import org.zerock.datie_boot.entity.User;
import org.zerock.datie_boot.repository.UserRepository;
import org.zerock.datie_boot.service.CardCreationService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class CardCreationController {

    @Autowired
    private CardCreationService cardCreationService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @PostMapping("/lovercheck")
    public ResponseEntity<String> loverCheck(@RequestBody CardRequestDTO cardRequest) {
        Integer userno = cardCreationService.loverCheck(cardRequest.getId() , cardRequest.getPassword());
        if(userno == null){
            return ResponseEntity.ok("회원정보가 일치 하지 않습니다.");
        }else if (userno == 0 ){
            return ResponseEntity.ok("회원의 카드가 이미 존재 합니다.");
        } else {
            return ResponseEntity.ok(String.valueOf(userno));
        }

    }





    @GetMapping("/userInfoByNo")
    public ResponseEntity<ArrayList<User>> getUserInfo(@RequestParam Integer userno1, @RequestParam Integer userno2) {
        // 서비스 메서드를 호출하여 사용자 정보를 가져옵니다.
        User user1 = cardCreationService.findByUserno(userno1);
        User user2 = cardCreationService.findByUserno(userno2);

        // 두 사용자 정보를 하나의 응답으로 묶어서 반환합니다.
        ArrayList<User> userinfo = new ArrayList<>();
        userinfo.add(user1);
        userinfo.add(user2);
        return ResponseEntity.ok(userinfo);
    }


    @PostMapping("/creationCard")
    public ResponseEntity<String> createCard(@RequestBody List<CardRequestDTO> userDataList) {
        try {
            // 각 사용자 데이터 처리
            for (CardRequestDTO userData : userDataList) {
                cardCreationService.createCard(userData);
            }
            return ResponseEntity.ok("카드 정보가 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 오류: " + e.getMessage());
        }
    }



}
