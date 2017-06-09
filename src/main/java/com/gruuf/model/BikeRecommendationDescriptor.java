package com.gruuf.model;

public class BikeRecommendationDescriptor {

    private final UserLocale locale;
    private final BikeRecommendation recommendation;

    public BikeRecommendationDescriptor(UserLocale locale, BikeRecommendation recommendation) {
        this.locale = locale;
        this.recommendation = recommendation;
    }

    public String getId() {
        return recommendation.getId();
    }

    public String getEnglishDescription() {
        return recommendation.getEnglishDescription();
    }

    public boolean isNotify() {
        return recommendation.isNotify();
    }

    public User getRequestedByUser() {
        return recommendation.getRequestedByUser();
    }

    public boolean isApproved() {
        return recommendation.isApproved();
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
