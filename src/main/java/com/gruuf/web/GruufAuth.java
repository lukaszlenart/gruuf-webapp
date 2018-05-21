package com.gruuf.web;

import org.apache.commons.lang.RandomStringUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class GruufAuth {

    public static final SecureRandom RANDOM = new SecureRandom();

    public static final String AUTH_TOKEN = generateUUID();

    public static String generateUUID() {
        return new BigInteger(165, RANDOM).toString(36).toUpperCase();
    }

    public static String randomString() {
        return RandomStringUtils.randomAlphanumeric(12);
    }

    public static String hash(String value, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(value.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
