package org.zerock.datie_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.datie_boot.entity.Pay;
import org.zerock.datie_boot.repository.PayRepository;

@Service
public class PayService {

    @Autowired
    private PayRepository payRepository;

    public Pay showInfo(Pay pay) {
        return payRepository.save(pay);
    }

}

