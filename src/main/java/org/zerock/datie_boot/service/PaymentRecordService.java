package org.zerock.datie_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.datie_boot.entity.PaymentRecord;
import org.zerock.datie_boot.repository.PaymentRecordRepository;

@Service
public class PaymentRecordService {

    @Autowired
    private PaymentRecordRepository paymentRecordRepository;

    public PaymentRecord showInfo(PaymentRecord paymentrecord) {
        return paymentRecordRepository.save(paymentrecord);
    }

}

