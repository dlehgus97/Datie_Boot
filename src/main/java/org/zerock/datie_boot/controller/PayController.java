package org.zerock.datie_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zerock.datie_boot.entity.Pay;
import org.zerock.datie_boot.service.PayService;

@RestController
public class PayController {

    @Autowired
    private PayService payService;

    @PostMapping("/pay/PayInfo")
    public Pay payInfo(@RequestBody Pay payEntity) {
        return payService.showInfo(payEntity);
    }

}
