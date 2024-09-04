package org.zerock.datie_boot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.zerock.datie_boot.entity.Card;
import org.zerock.datie_boot.entity.PaymentRecord;
import org.zerock.datie_boot.gpt.GptRequest;
import org.zerock.datie_boot.gpt.GptResponse;
import org.zerock.datie_boot.service.CardService;
import org.zerock.datie_boot.service.PaymentRecordService;
import org.zerock.datie_boot.service.UserService;

import java.util.*;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://ec2-13-53-91-123.eu-north-1.compute.amazonaws.com", "http://13.53.91.123"})
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentRecordService paymentRecordService;
    @Autowired
    private CardService cardService;

    @Value("${openai.model}")
    private String model;
    @Value("${openai.api.url}")
    private String apiURL;
    @Autowired
    private RestTemplate template;

//    @PostMapping("/user/{userno}")
//    public Optional<User> getUser(@PathVariable int userno) {
//        return userService.getUserByUserno(userno);
//    }

    @PostMapping("/card/{cardno}/payment-records")
    public Page<PaymentRecord> getPaymentRecords(
            @PathVariable int cardno,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page, size);
        return paymentRecordService.getPaymentRecordsByCardno(cardno, pageable);
    }

    @PostMapping("/card/{cardno}/payment-records-all")
    public List<PaymentRecord> getAllPaymentRecords(@PathVariable int cardno) {
        return paymentRecordService.getAllPaymentRecordsByCardno(cardno);
    }

    @PostMapping("/card/{cardno}/payment-records-month")
    public List<PaymentRecord> getPaymentRecordsMonth(
            @PathVariable int cardno,
            @RequestParam int year,
            @RequestParam int month) {

        return paymentRecordService.getPaymentRecordsByMonth(cardno, year, month);
    }

    @PostMapping("/card/{cardno}")
    public Card getCardInfo(@PathVariable int cardno) {
        return cardService.getCardInfo(cardno);
    }

    @PostMapping("/recommend/{cardno}")
    public ResponseEntity<?> getRecommendation(@PathVariable int cardno, @RequestParam int page, @RequestParam int size) throws JsonProcessingException {
        // 1. 최근 거래 내역 조회
        Pageable pageable = PageRequest.of(page, size);
        Page<PaymentRecord> paymentRecords = paymentRecordService.getPaymentRecordsByCardno(cardno, pageable);

        if (paymentRecords.isEmpty()) {
            return ResponseEntity.ok("추천없음");
        }

        // 2. 프롬프트 생성
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("카테고리, 금액, 구매내역이름을 제공할거야 이 내용을 바탕으로 액티비티나 할만한 것들을 추천해줘\n" +
                "1. 추천은 카테고리 기반으로 적절하게 추천할 것 (예를 들어 외식이 많으면 활동적인 액티비티나 외식을 추천)\n" +
                "2. 금액을 생각하여 비슷한 금액대의 활동을 추천할 것\n" +
                "3. location은 구매한 곳의 지점 이름, 카카오맵에 검색하여 위치를 산출해서 근처로 추천할 것\n" +
                "4. 대답은 주소에 상호명까지만 붙여서 단답으로 대답해줄 것\n" +
                "5. 추천은 세가지를 해줄 것\n" +
                "6. json 형태로 대답할 것\n" +
                "7. 예시 (최근 활동적인 소비가 많을 때)\n" +
                "Activity: 더 클라이밍 연남점\n" +
                "Location: 서울 마포구 양화로 186 3층\n" +
                "Basis: 외식\n" +
                "8. 내부에 불필요한 설명 뺄 것\n" +
                "9. 어떤 카테고리를 기준으로 산출했는지 추가할 것(최근 10개중 가장 많은것이 외식이면 Basis: 외식)\n" +
                "10. 추천할 장소나 활동이 실제로 존재하며, Google Maps 또는 Kakao Maps에서 검색 결과로 나타나는지 확인할 것.\n" +
                "11. 연인들이 하기 적절한 것으로 추천할 것\n");

        for (PaymentRecord record : paymentRecords) {
            promptBuilder.append("Category: ").append(record.getCategory()).append("\n");
            promptBuilder.append("Amount: ").append(record.getAmount()).append("\n");
            promptBuilder.append("Location: ").append(record.getContent()).append("\n\n");
        }

        String prompt = promptBuilder.toString();

        // 3. GPT API 호출
        GptRequest request = new GptRequest(model, prompt);
        GptResponse gptResponse = template.postForObject(apiURL, request, GptResponse.class);

        if (gptResponse == null || gptResponse.getChoices().isEmpty()) {
            return ResponseEntity.ok("추천없음");
        }

        String gptResponseContent = gptResponse.getChoices().get(0).getMessage().getContent();

        String[] lines = gptResponseContent.split("\n");
        if (lines.length > 2) {
            gptResponseContent = String.join("\n", Arrays.copyOfRange(lines, 1, lines.length - 1));
        }

        System.out.println("GPT Response Content: " + gptResponseContent);

        System.out.println(gptResponseContent);
        // 4. 최종 결과 리턴
        // JSON 파싱 예시 (Jackson 사용)
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, String>> recommendations = objectMapper.readValue(gptResponseContent, new TypeReference<List<Map<String, String>>>(){});

        return ResponseEntity.ok(recommendations);
    }

    @PostMapping("/payment-record/{payno}/category")
    public ResponseEntity<PaymentRecord> updateCategory(
            @PathVariable Long payno,
            @RequestBody String newCategory) {
        System.out.println(newCategory);
        newCategory = newCategory.replace("\"", "");
        System.out.println(newCategory);
        PaymentRecord updatedRecord = paymentRecordService.updateCategory(payno, newCategory);
        return ResponseEntity.ok(updatedRecord);
    }

}
