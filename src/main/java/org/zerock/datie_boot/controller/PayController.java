package org.zerock.datie_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.datie_boot.entity.Card;
import org.zerock.datie_boot.entity.Company;
import org.zerock.datie_boot.entity.PaymentRecord;
import org.zerock.datie_boot.entity.User;
import org.zerock.datie_boot.repository.CardRepository;
import org.zerock.datie_boot.repository.CompanyRepository;
import org.zerock.datie_boot.repository.UserRepository;
import org.zerock.datie_boot.service.CardService;
import org.zerock.datie_boot.service.PaymentRecordService;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class PayController {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private PaymentRecordService paymentRecordService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardService cardService;
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

    //결제info에서
    @GetMapping("/company")
    public ResponseEntity<Company> getCompanyById(@RequestParam("companyno") int companyno) {
        Company company = companyRepository.findById(companyno).orElse(null);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(company);
    }
    //결제info에서
    @GetMapping("/id")
    public ResponseEntity<User>getUserById(@RequestParam("id") String id) {
        System.out.println("!!!API발동!!!");
        User user = userRepository.findByUserId(id).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        System.out.println("AAA"+user.getUserno());
        return ResponseEntity.ok(user);
    }

    //결제info에서
    @GetMapping("/card")
    public ResponseEntity<Map<String, String>> getCardUserNames(@RequestParam("userno") int userno) {
        Map<String, String> names = cardService.getNamesByCardUserno(userno);
        System.out.println("CCC"+names);
        if (names.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(names);
    }
}
