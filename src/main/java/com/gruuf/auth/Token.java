package com.gruuf.auth;

import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.Set;

public enum Token {

    USER, ADMIN;

    public static Set<Token> all() {
        return Collections.unmodifiableSet(Sets.newHashSet(USER, ADMIN));
    }
}
