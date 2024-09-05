package org.zerock.datie_boot.qr;

import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.datie_boot.entity.PaymentKey;
import org.zerock.datie_boot.repository.PaymentKeyRepository;

import java.io.IOException;
import java.util.UUID;

@CrossOrigin(origins = {"http://localhost:3000/", "http://localhost/"})
@RestController
public class QrController {

    private final String address ="http://ec2-13-53-91-123.eu-north-1.compute.amazonaws.com/";
    private final QrServiceImple qrServiceImple;
    private final PaymentKeyRepository paymentKeyRepository;

    @Autowired
    public QrController(QrServiceImple qrServiceImple, PaymentKeyRepository paymentKeyRepository) {
        this.qrServiceImple = qrServiceImple;
        this.paymentKeyRepository = paymentKeyRepository;
    }

    @GetMapping("/qr")
    public Object createQR(
            @RequestParam String url,
            @RequestParam String amount) throws WriterException, IOException {

        // UUID 생성
        String uuid = UUID.randomUUID().toString();

        // 선택된 URL에 추가적인 정보(파라미터)를 포함하여 최종 URL 생성
        String fullUrl = address+url + "&amount=" + amount + "&key=" + uuid;

        // PaymentKey 엔티티 생성 및 저장
        PaymentKey paymentKey = new PaymentKey(uuid, false);
        paymentKeyRepository.save(paymentKey);

        // QR 코드 생성
        Object qr = qrServiceImple.createQR(fullUrl);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qr);
    }
}
