package com.gruuf.model;

// https://countrycode.org/
public enum Country {

    POL, GBR, DEU;

    public String getKey() {
        return "country." + name().toLowerCase();
    }

}
