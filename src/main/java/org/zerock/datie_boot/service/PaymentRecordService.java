package org.zerock.datie_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.zerock.datie_boot.entity.Account;
import org.zerock.datie_boot.entity.Card;
import org.zerock.datie_boot.entity.Diary;
import org.zerock.datie_boot.entity.PaymentRecord;
import org.zerock.datie_boot.gpt.GptRequest;
import org.zerock.datie_boot.gpt.GptResponse;
import org.zerock.datie_boot.repository.AccountRepository;
import org.zerock.datie_boot.repository.CardRepository;
import org.zerock.datie_boot.repository.DiaryRepository;
import org.zerock.datie_boot.repository.PaymentRecordRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentRecordService {

    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private PaymentRecordRepository paymentRecordRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    @Autowired
    private RestTemplate template;

    public PaymentRecord showInfo(PaymentRecord paymentrecord) {
        return paymentRecordRepository.save(paymentrecord);
    }

    public Page<PaymentRecord> getPaymentRecordsByCardno(int cardno, Pageable pageable) {
        return paymentRecordRepository.findByCardno(cardno, pageable);
    }

    public List<PaymentRecord> getAllPaymentRecordsByCardno(int cardno) {
        return paymentRecordRepository.findAllByCardno(cardno);
    }

    public PaymentRecord updateCategory(Long payno, String newCategory) {
        Optional<PaymentRecord> optionalRecord = paymentRecordRepository.findById(payno);
        if (optionalRecord.isPresent()) {
            PaymentRecord paymentRecord = optionalRecord.get();
            paymentRecord.setCategory(newCategory);
            return paymentRecordRepository.save(paymentRecord);
        } else {
            throw new RuntimeException("PaymentRecord not found with id: " + payno);
        }
    }

    public List<PaymentRecord> getPaymentRecordsByMonth(int cardno, int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1); // 월은 0부터 시작하므로 -1 해줍니다.
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Timestamp startDate = new Timestamp(calendar.getTimeInMillis());

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Timestamp endDate = new Timestamp(calendar.getTimeInMillis());

        return paymentRecordRepository.findByCardnoAndConfirmdateBetween(cardno, startDate, endDate);
    }

    public String processPayment(PaymentRecord paymentRecord) {
        PaymentRecord PR = new PaymentRecord();
        int cardno = paymentRecord.getCardno();
        int peramount = paymentRecord.getPeramount();
        Card card = cardRepository.findByCardno(cardno);



        PR.setCardno(cardno);
        PR.setCompanyno(paymentRecord.getCompanyno());
        PR.setContent(paymentRecord.getContent());
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

                //gpt 카테고리 산출
                System.out.println("카테고리 불러오기");
                String prompt = "소비내역의 내용이야. 이 내용을 구글에 검색해 보고 내가 제시한 카테고리중 어디에 포함될지 유추한 뒤에 괄호를 뺀 카테고리만 말해." +
                        " 내용=" + PR.getContent() +
                        " 카테고리=식료품 (마트, 편의점 등에서 구매한 식료품) 외식 (레스토랑, 패스트푸드, 카페 등에서의 식사) 교통비 (버스, 지하철, 택시, 주유비 등) 의료비 (병원, 약국 등에서 발생한 의료 관련 비용) 쇼핑 (의류, 전자제품, 가구 등 물품 구매) 공과금 (전기, 가스, 수도, 통신비 등) 보험료 (생명보험, 건강보험, 자동차보험 등) 교육비 (학원비, 교재 구입 등) 문화/여가 (영화, 공연, 운동, 여행 등) 인터넷/통신비 (인터넷 요금, 휴대폰 요금 등) 기부/후원 (기부금, 후원금 등) 자동차 관련 비용 (차량 유지비, 주차비, 자동차 수리비 등) 미용/패션 (미용실, 화장품, 액세서리 등) 가정생활 (청소 용품, 주방 용품, 가구 등) 이자/대출 상환 (이자 지급, 대출 원금 상환 등) 세금 (소득세, 지방세, 부가세 등) 회원비 (헬스장, 구독 서비스 등) 선물/경조사 (경조사비, 선물 구입 등) 기타 (특정 분류에 해당하지 않는 기타 지출) 저축/투자 (저축, 주식 구매, 펀드 투자 등)";
                GptRequest request = new GptRequest(model, prompt);
                System.out.println("prompt : " + prompt);
                System.out.println("request : " + request);
                GptResponse gptResponse = template.postForObject(apiURL, request, GptResponse.class);
                System.out.println("Response : " + gptResponse);

                String category = "";
                if (gptResponse == null || gptResponse.getChoices() == null || gptResponse.getChoices().isEmpty()) {
                    category = "기타";
                }

                category = gptResponse.getChoices().get(0).getMessage().getContent();
                PR.setCategory(category);
                PR.setPaystate(1);
                paymentRecordRepository.save(PR);
                int payno = PR.getPayno();
                //다이어리에 넣어주기
                Diary dr =new Diary();
                dr.setPayNo(payno);
                diaryRepository.save(dr);
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

