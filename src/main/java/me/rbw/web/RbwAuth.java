package me.rbw.web;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RbwAuth {

    public static final SecureRandom RANDOM = new SecureRandom();

    public static final String AUTH_TOKEN = new BigInteger(165, RANDOM).toString(36).toUpperCase();

}
