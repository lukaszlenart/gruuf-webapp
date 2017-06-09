package com.gruuf.model;

public class RecommendationDescriptor {

    private UserLocale locale;
    private final BikeRecommendation recommendation;
    private final BikeEvent bikeEvent;

    public RecommendationDescriptor(UserLocale locale, BikeRecommendation recommendation, BikeEvent bikeEvent) {
        this.locale = locale;
        this.recommendation = recommendation;
        this.bikeEvent = bikeEvent;
    }

    public boolean isFulfilled() {
        return this.bikeEvent != null;
    }

    public BikeRecommendationDescriptor getRecommendation() {
        return new BikeRecommendationDescriptor(locale, recommendation);
    }

    public BikeEventDescriptor getBikeEvent() {
        return new BikeEventDescriptor(locale, bikeEvent);
    }
}
