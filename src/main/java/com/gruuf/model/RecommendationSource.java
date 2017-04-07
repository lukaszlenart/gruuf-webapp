package com.gruuf.model;

public enum RecommendationSource {

    SERVICE_MANUAL, COMMUNITY;

    public String getKey() {
        return "source." + name().toLowerCase();
    }
}
