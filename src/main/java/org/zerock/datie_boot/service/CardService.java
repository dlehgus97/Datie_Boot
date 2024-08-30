package org.zerock.datie_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.datie_boot.dto.PasswordChangeRequestDTO;
import org.zerock.datie_boot.entity.Card;
import org.zerock.datie_boot.entity.User;
import org.zerock.datie_boot.repository.CardRepository;
import org.zerock.datie_boot.repository.UserRepository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private UserRepository userRepository;
    public Card getCardInfo(int cardno) {
        return cardRepository.findByCardno(cardno);
    }

    public boolean changeCardPassword(PasswordChangeRequestDTO passwordChangeRequestDTO) {
        System.out.println("Attempting to change password with DTO: " + passwordChangeRequestDTO);

        // userno를 통해 카드 정보를 찾습니다.
        Optional<Card> cardOptional = cardRepository.findByUserno(passwordChangeRequestDTO.getUserno());

        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();

            // 현재 비밀번호가 일치하는지 확인합니다.
            if (card.getCardpw() == Integer.parseInt(passwordChangeRequestDTO.getCurrentPassword())) {
                // 새 비밀번호로 변경합니다.
                card.setCardpw(Integer.parseInt(passwordChangeRequestDTO.getNewPassword()));

                // 카드 정보를 저장합니다.
                cardRepository.save(card);
                System.out.println("Password successfully changed.");
                return true;
            } else {
                System.out.println("Current password does not match.");
            }
        } else {
            System.out.println("Card not found for userno: " + passwordChangeRequestDTO.getUserno());
        }
        return false;
    }
    public boolean cancelCard(PasswordChangeRequestDTO passwordChangeRequestDTO) {
        int userno = passwordChangeRequestDTO.getUserno();
        Optional<Card> cardOptional = cardRepository.findByUserno(userno);

        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();

            // 비밀번호 확인
            if (card.getCardpw() == Integer.parseInt(passwordChangeRequestDTO.getCurrentPassword())) {
                card.setCStatus(3);
                cardRepository.save(card); // 변경 사항 저장
                return true;
            }
        }
        return false;
    }

    public boolean lostCard(PasswordChangeRequestDTO passwordChangeRequestDTO) {
        int userno = passwordChangeRequestDTO.getUserno();
        Optional<Card> cardOptional = cardRepository.findByUserno(userno);

        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();

            // 비밀번호 확인
            if (card.getCardpw() == Integer.parseInt(passwordChangeRequestDTO.getCurrentPassword())) {
                card.setCStatus(4);
                cardRepository.save(card); // 변경 사항 저장
                return true;
            }
        }
        return false;
    }
    public Map<String, String> getNamesByCardUserno(int userno) {
        Map<String, String> namesMap = new HashMap<>();

        Card card = cardRepository.findByUserno(userno).orElse(null);
        if (card != null) {
            Optional<User> user1 = userRepository.findByUserno(card.getUserno());
            Optional<User> user2 = userRepository.findByUserno(card.getUserno2());

            user1.ifPresent(user -> namesMap.put("userno_name", user.getName()));
            user2.ifPresent(user -> namesMap.put("userno2_name", user.getName()));
        }

        return namesMap;
    }
}
