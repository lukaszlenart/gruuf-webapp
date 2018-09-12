package com.gruuf.web.actions.bike;

public class SelectOption {

    private String key;
    private String label;

    public SelectOption(String key, String label) {
        this.key = key;
        this.label = label;
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }
}
