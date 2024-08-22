package org.zerock.datie_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.datie_boot.entity.Account;
import org.zerock.datie_boot.entity.Card;
import org.zerock.datie_boot.entity.PaymentRecord;
import org.zerock.datie_boot.repository.AccountRepository;
import org.zerock.datie_boot.repository.CardRepository;
import org.zerock.datie_boot.repository.PaymentRecordRepository;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class PaymentRecordService {

    @Autowired
    private PaymentRecordRepository paymentRecordRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private AccountRepository accountRepository;

    public PaymentRecord showInfo(PaymentRecord paymentrecord) {
        return paymentRecordRepository.save(paymentrecord);
    }


    public String processPayment(PaymentRecord paymentRecord) {
        PaymentRecord PR = new PaymentRecord();
        int cardno = paymentRecord.getCardno();
        int peramount = paymentRecord.getPeramount();
        Card card = cardRepository.findByCardno(cardno);

        PR.setCardno(cardno);
        PR.setCompanyno(paymentRecord.getCompanyno());
        PR.setAmount(paymentRecord.getAmount());
        PR.setPeramount(peramount);
        PR.setBonus(paymentRecord.getBonus());
        PR.setConfirmdate(new Timestamp(new Date().getTime()));
        if (card != null) {
            int userno = card.getUserno();
            int userno2 = card.getUserno2();

            System.out.println(userno + " " + userno2);

            //1번놈2번놈 계좌가져오기
            Account account = accountRepository.findByUserno(userno);
            Account account2 = accountRepository.findByUserno(userno2);

            if (account.getBalance() >= peramount
                    && account2.getBalance() >= peramount) {
                System.out.println("잔액확인,결제진행함요");
                account.setBalance(account.getBalance() - peramount);
                account2.setBalance(account2.getBalance() - peramount);

                System.out.println(account.getBalance());
                System.out.println(account2.getBalance());

                accountRepository.save(account);
                accountRepository.save(account2);
                PR.setPaystate(1);
                paymentRecordRepository.save(PR);
                return "결제 성공";

            } else {
                PR.setPaystate(0);
                paymentRecordRepository.save(PR);
                System.out.println("잔액부족");
                return "잔액 부족";
            }


        } else {
            System.out.println("fucking 카드 조회 실패");
            return "카드 조회 실패";
        }

    }
}

