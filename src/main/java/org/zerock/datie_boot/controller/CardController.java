package org.zerock.datie_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.datie_boot.entity.Card;
import org.zerock.datie_boot.entity.User;
import org.zerock.datie_boot.repository.CardRepository;
import org.zerock.datie_boot.service.CardService;
import org.zerock.datie_boot.dto.PasswordChangeRequestDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class CardController {

    @Autowired
    private CardRepository cardRepository;


    @Autowired
    private CardService cardService;

    @PostMapping("/cardpassword")
    public ResponseEntity<Map<String, Integer>> getCardPassword(@RequestBody User user) {
        int userno = user.getUserno();
        Optional<Card> cardOptional = cardRepository.findByUserno(userno);

        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();
            // 응답으로 반환할 데이터 준비
            Map<String, Integer> response = new HashMap<>();
            response.put("cardno", card.getCardno());
            response.put("cardpw", card.getCardpw());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/changepassword/{userno}")
    public ResponseEntity<String> changeCardPassword(
            @PathVariable int userno,
            @RequestBody PasswordChangeRequestDTO passwordChangeRequestDTO) {

        // userno를 DTO에 설정
        passwordChangeRequestDTO.setUserno(userno);

        boolean isChanged = cardService.changeCardPassword(passwordChangeRequestDTO);


        if (isChanged) {
            System.out.println("Password change successful for userno: " + userno);
            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        } else {
            System.out.println("Password change failed for userno: " + userno);
            return ResponseEntity.badRequest().body("비밀번호 변경에 실패했습니다.");
        }
    }
}
