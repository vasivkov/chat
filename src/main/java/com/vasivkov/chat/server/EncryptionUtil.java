package com.vasivkov.chat.server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by vasya on 16/07/17.
 */
public class EncryptionUtil {

    private static final MessageDigest SHA1;

    static {
        try {
            SHA1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1 algorithm not found");
        }
    }

    public static String generateHash(String input) {
        StringBuilder hash = new StringBuilder();

        byte[] hashedBytes = SHA1.digest(input.getBytes());
        char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        for (int idx = 0; idx < hashedBytes.length; ++idx) {
            byte b = hashedBytes[idx];
            hash.append(digits[(b & 0xf0) >> 4]);
            hash.append(digits[b & 0x0f]);
        }
        return hash.toString();

    }
}