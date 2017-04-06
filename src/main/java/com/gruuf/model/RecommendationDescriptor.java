package com.gruuf.model;

public class RecommendationDescriptor {

    private final BikeRecommendation recommendation;
    private final BikeEvent bikeEvent;

    public RecommendationDescriptor(BikeRecommendation recommendation, BikeEvent bikeEvent) {
        this.recommendation = recommendation;
        this.bikeEvent = bikeEvent;
    }

    public boolean isFulfilled() {
        return this.bikeEvent != null;
    }

    public BikeRecommendation getRecommendation() {
        return recommendation;
    }

    public BikeEvent getBikeEvent() {
        return bikeEvent;
    }
}
