package com.gruuf.model;

// https://countrycode.org/
public enum Country {

    POL(UserLocale.PL), GBR(UserLocale.UK), DEU(UserLocale.DE), USA(UserLocale.EN);

    private UserLocale userLocale;

    Country(UserLocale userLocale) {
        this.userLocale = userLocale;
    }

    public String getKey() {
        return "country." + name().toLowerCase();
    }

    public static Country fromLocale(UserLocale userLocale) {
        for (Country country : values()) {
            if (country.userLocale.equals(userLocale)) {
                return country;
            }
        }
        return null;
    }
}
