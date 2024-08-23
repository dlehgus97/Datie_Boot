package org.zerock.datie_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.datie_boot.entity.Card;
import org.zerock.datie_boot.repository.CardRepository;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    public Card getCardInfo(int cardno) {
        return cardRepository.findByCardno(cardno);
    }
}
