package com.gruuf.model;

import com.google.appengine.repackaged.com.google.common.collect.Sets;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

public enum UserLocale {

    EN, PL;

    public static Set<UserLocale> all() {
        return Collections.unmodifiableSet(Sets.newHashSet(EN, PL));
    }

    public Locale toLocale() {
        return new Locale(name().toLowerCase());
    }
}
