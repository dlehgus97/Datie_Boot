package org.zerock.datie_boot.service;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.datie_boot.dto.CardRequestDTO;
import org.zerock.datie_boot.entity.Card;
import org.zerock.datie_boot.entity.User;
import org.zerock.datie_boot.repository.CardRepository;
import org.zerock.datie_boot.repository.UserRepository;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;
import java.util.Random;

@Service
public class CardCreationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CardRepository cardRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Integer loverCheck(String id, String password) {
        Optional<User> userOptional = userRepository.findByUserId(id);

        if(userOptional.isPresent()){
            if(!passwordEncoder.matches(password , userOptional.get().getPw())){
                return null;
            }
        }else {
            return null;
        }


        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getUserno(); // userNo를 반환
        } else {
            return null; // 유저가 존재하지 않을 경우 null 반환
        }
    }

    public User findByUserno(Integer userno1) {
        Optional<User> byUserno = userRepository.findByUserno(userno1);
        return byUserno.orElse(null); // 사용자 정보가 없을 경우 null 반환
    }


    @Transactional
    public void createCard(CardRequestDTO userData) {

        String serialNumber = null;
        while(true){
            String serialNumberTemp = generateCreditCardNumber();
            if(cardRepository.findBySerialNumber(serialNumberTemp).isPresent()){

            }else {
                serialNumber = serialNumberTemp;
                break;
            }

        }
        String cvc = generateCVC();

        Card card = new Card();
        card.setCardpw(Integer.parseInt(userData.getCardpw()));
        card.setUserno(userData.getUserno());
        card.setUserno2(userData.getUsernoLover());
        card.setCStatus(userData.getCstatus());
        card.setCModdate(new Timestamp(System.currentTimeMillis()));
        card.setCardtypeno(userData.getCardtypeno());
        card.setCvc(Integer.parseInt(cvc));


        // 현재 시간으로부터 5년 후 자정 시간 설정
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 5); // 5년 추가
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Timestamp expirationDate = new Timestamp(calendar.getTimeInMillis());
        card.setDate(expirationDate);


        card.setTitleHolder(String.valueOf(userData.getTitleHolder()));
        card.setInitials(userData.getInitial());
        card.setSerialNumber(serialNumber);


        Card savedCard = cardRepository.save(card);

        userRepository.updateCardnoByUserno(savedCard.getCardno() , savedCard.getUserno());



    }


    public static String generateCreditCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();

        // 16자리 숫자 생성
        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }

        return cardNumber.toString();
    }

    public static String generateCVC() {
        Random random = new Random();
        StringBuilder cvc = new StringBuilder();

        // 3자리 숫자 생성
        for (int i = 0; i < 3; i++) {
            cvc.append(random.nextInt(10));
        }

        return cvc.toString();
    }
}
