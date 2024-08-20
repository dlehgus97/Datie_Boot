package org.zerock.datie_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zerock.datie_boot.entity.PaymentRecord;
import org.zerock.datie_boot.service.PaymentRecordService;

@RestController
public class PayController {

    @Autowired
    private PaymentRecordService paymentRecordService;

    @PostMapping("/pay/PayInfo")
    public PaymentRecord payInfo(@RequestBody PaymentRecord payEntity) {
        return paymentRecordService.showInfo(payEntity);
    }

}
