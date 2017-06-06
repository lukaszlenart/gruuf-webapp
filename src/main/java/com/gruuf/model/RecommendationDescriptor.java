package com.gruuf.model;

public class RecommendationDescriptor {

    private final BikeRecommendation recommendation;
    private final BikeEventDescriptor bikeEvent;

    public RecommendationDescriptor(BikeRecommendation recommendation, BikeEvent bikeEvent) {
        this.recommendation = recommendation;
        this.bikeEvent = new BikeEventDescriptor(bikeEvent);
    }

    public boolean isFulfilled() {
        return this.bikeEvent != null;
    }

    public BikeRecommendation getRecommendation() {
        return recommendation;
    }

    public BikeEventDescriptor getBikeEvent() {
        return bikeEvent;
    }
}
