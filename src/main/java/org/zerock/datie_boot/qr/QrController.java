package org.zerock.datie_boot.qr;


import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

//@CrossOrigin(origins = {"http://localhost:3000/","http://localhost/"})
@RestController
public class QrController {
    private QrServiceImple qrServiceImple;

    @Autowired
    public QrController(QrServiceImple qrServiceImple) {
        this.qrServiceImple = qrServiceImple;
    }

    @GetMapping("/qr")
    public Object cerateQR(@RequestParam String url) throws WriterException, IOException {
        Object Qr = qrServiceImple.createQR(url);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(Qr);
    }
}