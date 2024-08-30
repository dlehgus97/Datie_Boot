package org.zerock.datie_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zerock.datie_boot.entity.Card;
import org.zerock.datie_boot.entity.PaymentRecord;
import org.zerock.datie_boot.entity.User;
import org.zerock.datie_boot.service.CardService;
import org.zerock.datie_boot.service.PaymentRecordService;
import org.zerock.datie_boot.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://ec2-13-53-91-123.eu-north-1.compute.amazonaws.com", "http://13.53.91.123"})
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentRecordService paymentRecordService;
    @Autowired
    private CardService cardService;

//    @PostMapping("/user/{userno}")
//    public Optional<User> getUser(@PathVariable int userno) {
//        return userService.getUserByUserno(userno);
//    }

    @PostMapping("/card/{cardno}/payment-records")
    public List<PaymentRecord> getPaymentRecords(@PathVariable int cardno) {
        return paymentRecordService.getPaymentRecordsByCardno(cardno);
    }

    @PostMapping("/card/{cardno}")
    public Card getCardInfo(@PathVariable int cardno) {
        return cardService.getCardInfo(cardno);
    }
}
