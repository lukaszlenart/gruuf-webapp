package me.rbw.services;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import me.rbw.model.Bike;
import me.rbw.model.BikeEvent;

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
