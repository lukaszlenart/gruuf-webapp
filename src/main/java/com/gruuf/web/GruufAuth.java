package com.gruuf.web;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.security.SecureRandom;
import java.util.UUID;

public class GruufAuth {

    private static final Logger LOG = LogManager.getLogger(GruufAuth.class);

    public static final SecureRandom RANDOM = new SecureRandom();

    public static final String AUTH_TOKEN = generateUUID();

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String randomString() {
        return RandomStringUtils.randomAlphanumeric(12);
    }

    public static String hash(String value) {
        return BCrypt.hashpw(value, BCrypt.gensalt());
    }

    public static boolean verifyHash(String password, String hash) {
        try {
            return BCrypt.checkpw(password, hash);
        } catch (Throwable t) {
            LOG.warn("Wrong hash!", t);
            return false;
        }
    }

}
