package com.gruuf.model;

import com.google.appengine.repackaged.com.google.common.collect.Sets;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

public enum UserLocale {

    EN(Constants.EN_DATE_FORMAT), PL(Constants.PL_DATE_FORMAT);

    private String dateFormat;

    UserLocale(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public static Set<UserLocale> all() {
        return Collections.unmodifiableSet(Sets.newHashSet(EN, PL));
    }

    public Locale toLocale() {
        return new Locale(name().toLowerCase());
    }

    public String getDateFormat() {
        return dateFormat;
    }

    private static class Constants {
        public static final String EN_DATE_FORMAT = "dd/MM/yyyy";
        public static final String PL_DATE_FORMAT = "yyyy-MM-dd";
    }
}
