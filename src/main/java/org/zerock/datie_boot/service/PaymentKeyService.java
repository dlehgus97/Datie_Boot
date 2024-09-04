package org.zerock.datie_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.datie_boot.entity.PaymentKey;
import org.zerock.datie_boot.repository.PaymentKeyRepository;

@Service
public class PaymentKeyService {

    @Autowired
    private PaymentKeyRepository paymentKeyRepository;

    public String paymentkeyUsed(String key) {
        PaymentKey PK = paymentKeyRepository.findByKeyValue(key);

        if (PK != null) {
            PK.setUsed(true);
            paymentKeyRepository.save(PK);
            return "Key 사용";
        } else {
            return "Key 찾기 오류";
        }
    }

    public int checkUsed(String key) {
        PaymentKey PK = paymentKeyRepository.findByKeyValue(key);

        if(PK != null) {
            if(PK.isUsed()) {
                return 0;//사용되었으면
            }else {
                return 1;//결제 전
            }
        }else{
            return -1;//키를 찾을 수 없음
        }
    }
}
