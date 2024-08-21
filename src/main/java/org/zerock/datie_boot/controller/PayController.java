package org.zerock.datie_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.datie_boot.entity.PaymentRecord;
import org.zerock.datie_boot.service.PaymentRecordService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class PayController {

    @Autowired
    private PaymentRecordService paymentRecordService;

    @PostMapping("/pay/PayInfo")
    public PaymentRecord payInfo(@RequestBody PaymentRecord payEntity) {
        return paymentRecordService.showInfo(payEntity);
    }

    @PostMapping("/payresult")
    public ResponseEntity<String> handlePayment(@RequestBody PaymentRecord paymentRecord) {
        // processPayment 메서드를 호출하여 결과를 얻음
        String result = paymentRecordService.processPayment(paymentRecord);

        // 결과에 따라 적절한 HTTP 상태 코드와 응답 본문을 설정하여 반환
        if ("결제 성공".equals(result)) {
            return ResponseEntity.ok(result); // 200 OK와 함께 성공 메시지 반환
        } else if ("잔액 부족".equals(result)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result); // 400 Bad Request와 함께 잔액 부족 메시지 반환
        } else if ("카드 조회 실패".equals(result)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result); // 404 Not Found와 함께 카드 조회 실패 메시지 반환
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 오류 발생"); // 500 Internal Server Error와 함께 기본 오류 메시지 반환
        }
    }

}
