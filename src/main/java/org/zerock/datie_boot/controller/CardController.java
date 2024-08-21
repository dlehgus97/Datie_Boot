package org.zerock.datie_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.datie_boot.entity.Card;
import org.zerock.datie_boot.entity.User;
import org.zerock.datie_boot.repository.CardRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class CardController {

    @Autowired
    private CardRepository cardRepository;

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

}
