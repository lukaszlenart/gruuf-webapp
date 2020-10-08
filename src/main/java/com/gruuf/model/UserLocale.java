package com.gruuf.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public enum UserLocale {

    EN(Constants.EN_DATE_FORMAT, Constants.EN_DATE_PICKER_FORMAT, Locale.US),
    US(Constants.EN_DATE_FORMAT, Constants.EN_DATE_PICKER_FORMAT, Locale.US),
    UK(Constants.EN_DATE_FORMAT, Constants.EN_DATE_PICKER_FORMAT, Locale.UK),
    PL(Constants.PL_DATE_FORMAT, Constants.PL_DATE_PICKER_FORMAT, new Locale("pl", "PL")),
    DE(Constants.DE_DATE_FORMAT, Constants.DE_DATE_PICKER_FORMAT, Locale.GERMANY);

    private final String dateFormat;
    private final String datePickerFormat;
    private final Locale locale;

    UserLocale(String dateFormat, String datePickerFormat, Locale locale) {
        this.dateFormat = dateFormat;
        this.datePickerFormat = datePickerFormat;
        this.locale = locale;
    }

    public static Set<UserLocale> all() {
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(values())));
    }

    public static boolean isValidLocale(Locale locale) {
        for (UserLocale userLocale : values()) {
            if (userLocale.sameAs(locale)) {
                return true;
            }
        }
        return false;
    }

    public static UserLocale fromLocale(Locale locale) {
        for (UserLocale userLocale : values()) {
            if (userLocale.sameAs(locale)) {
                return userLocale;
            }
        }
        return null;
    }

    public Locale toLocale() {
        return locale;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public boolean sameAs(Locale locale) {
        return this.toLocale().getLanguage().equals(locale.getLanguage());
    }

    public String getDatePickerFormat() {
        return datePickerFormat;
    }

    @Override
    public String toString() {
        return "UserLocale{" +
                "dateFormat='" + dateFormat + '\'' +
                ", datePickerFormat='" + datePickerFormat + '\'' +
                ", locale=" + locale +
                '}';
    }

    private static class Constants {
        public static final String EN_DATE_FORMAT = "dd/MM/yyyy";
        public static final String EN_DATE_PICKER_FORMAT = "mm/dd/yy";

        public static final String PL_DATE_FORMAT = "yyyy-MM-dd";
        public static final String PL_DATE_PICKER_FORMAT = "yy-mm-dd";

        public static final String DE_DATE_FORMAT = "yyyy-MM-dd";
        public static final String DE_DATE_PICKER_FORMAT = "yy-mm-dd";
    }

}
