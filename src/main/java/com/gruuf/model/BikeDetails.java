package com.gruuf.model;

import java.util.List;

public class BikeDetails {

    private Bike bike;
    private User currentUser;
    private List<BikeEvent> events;

    public static BikeDetails create(Bike bike) {
        return new BikeDetails(bike);
    }

    public BikeDetails(Bike bike) {
        this.bike = bike;
    }

    public BikeDetails withUser(User currentUser) {
        this.currentUser = currentUser;
        return this;
    }

    public BikeDetails withHistory(List<BikeEvent> events) {
        this.events = events;
        return this;
    }

    public Bike getBike() {
        return bike;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public List<BikeEvent> getEvents() {
        return events;
    }

    @Override
    public String toString() {
        return "BikeDetails{" +
                "bike=" + bike +
                ", currentUser=" + currentUser +
                ", events=" + events +
                '}';
    }
}
