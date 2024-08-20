package org.zerock.datie_boot.qr;


import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = {"http://localhost:3000/","http://localhost/"})
@RestController
public class QrController {
    private QrServiceImple qrServiceImple;

    @Autowired
    public QrController(QrServiceImple qrServiceImple) {
        this.qrServiceImple = qrServiceImple;
    }

    @GetMapping("/qr")
    public Object createQR(
            @RequestParam String url,
            @RequestParam String amount) throws WriterException, IOException {

        // 선택된 URL에 추가적인 정보(파라미터)를 포함하여 최종 URL 생성
        String fullUrl = url + "&amount=" + amount;
        Object Qr = qrServiceImple.createQR(fullUrl);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(Qr);
    }

}