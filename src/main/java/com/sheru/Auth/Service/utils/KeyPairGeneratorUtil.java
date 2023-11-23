package com.sheru.Auth.Service.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class KeyPairGeneratorUtil {
    public static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.genKeyPair();
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
        return keyPair;
    }
}
