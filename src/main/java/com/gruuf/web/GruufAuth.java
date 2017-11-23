package com.gruuf.web;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.security.provider.MD5;

import java.math.BigInteger;
import java.security.SecureRandom;

public class GruufAuth {

    private static Logger LOG = LogManager.getLogger(GruufAuth.class);

    public static final SecureRandom RANDOM = new SecureRandom();

    public static final String AUTH_TOKEN = generateUUID();

    public static String generateUUID() {
        return new BigInteger(165, RANDOM).toString(36).toUpperCase();
    }

    public static String randomString() {
        return RandomStringUtils.randomAlphanumeric(12);
    }

    public static boolean isPasswordValid(String actual, String expected) {
        LOG.debug("Expected password {} and actual password {}", actual, expected);
        return expected.equals(actual);
    }

}
