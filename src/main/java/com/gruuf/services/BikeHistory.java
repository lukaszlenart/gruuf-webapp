package com.gruuf.services;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.gruuf.model.Bike;
import com.gruuf.model.BikeEvent;

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

}
