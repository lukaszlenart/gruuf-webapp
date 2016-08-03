package com.gruuf.services;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.gruuf.model.Bike;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.EventType;

import java.util.List;

public class BikeHistory {

    public List<BikeEvent> get(String bikeId) {
        Key<Bike> bike = Key.create(Bike.class, bikeId);

        return ObjectifyService
                .ofy()
                .load()
                .type(BikeEvent.class)
                .ancestor(bike)
                .list();
    }

    public void register(BikeEvent event) {
        ObjectifyService
                .ofy()
                .save()
                .entity(event)
                .now();
    }

    public List<EventType> listEventTypes() {
        return ObjectifyService
                .ofy()
                .load()
                .type(EventType.class)
                .order("name")
                .list();
    }

    public Key<EventType> addEventType(EventType event) {
        return ObjectifyService
                .ofy()
                .save()
                .entity(event)
                .now();
    }
}
