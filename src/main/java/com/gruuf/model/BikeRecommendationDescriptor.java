package com.gruuf.model;

public class BikeRecommendationDescriptor {

    private final UserLocale locale;
    private final BikeRecommendation recommendation;

    public BikeRecommendationDescriptor(UserLocale locale, BikeRecommendation recommendation) {
        this.locale = locale;
        this.recommendation = recommendation;
    }

    public EventTypeDescriptor getEventType() {
        return new EventTypeDescriptor(locale, recommendation.getEventType());
    }

    public RecommendationSource getSource() {
        return recommendation.getSource();
    }

    public Integer getMileagePeriod() {
        return recommendation.getMileagePeriod();
    }

    public Integer getMonthPeriod() {
        return recommendation.getMonthPeriod();
    }

    public Integer getMthPeriod() {
        return recommendation.getMthPeriod();
    }
}
