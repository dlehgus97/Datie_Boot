package org.zerock.datie_boot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zerock.datie_boot.entity.PaymentKey;
import org.zerock.datie_boot.service.PaymentKeyService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://ec2-13-53-91-123.eu-north-1.compute.amazonaws.com", "http://13.53.91.123"})
public class PaymentKeyController {

    @Autowired
    private PaymentKeyService paymentKeyService;

    @GetMapping("/check-key")
    public int checkKeyUsed(@RequestParam String key) {
        System.out.println("키 체크중");
        int result = paymentKeyService.checkUsed(key);
        return result;
    }
}
