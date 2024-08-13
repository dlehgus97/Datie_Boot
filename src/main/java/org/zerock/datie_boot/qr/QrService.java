package org.zerock.datie_boot.qr;

import com.google.zxing.WriterException;

import java.io.IOException;


public interface QrService {
    Object createQR(String url) throws WriterException, IOException;
}
