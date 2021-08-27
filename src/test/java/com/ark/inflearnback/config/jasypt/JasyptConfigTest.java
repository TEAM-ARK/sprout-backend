package com.ark.inflearnback.config.jasypt;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class JasyptConfigTest {

    @Value("${jasypt.config.password}")
    private String testPassword;

    @Test
    void jasyptTest() {
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(testPassword);

        String enc = pbeEnc.encrypt("테스트용 텍스트");
        System.out.println("enc = " + enc);

        String des = pbeEnc.decrypt(enc);
        System.out.println("des = " + des);
    }
}