package com.leon.springbootleon;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

public class TestEncode {

    @Test
    void encode() {
        var key = "test";
        var salt = "test";
        TextEncryptor encryptor = Encryptors.text(key, salt);
        System.out.println(encryptor.encrypt("test"));

    }
}
