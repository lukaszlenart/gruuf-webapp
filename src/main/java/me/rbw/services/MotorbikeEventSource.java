package me.rbw.services;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import me.rbw.model.Motorbike;
import me.rbw.model.MotorbikeEvent;

import java.util.List;

public class MotorbikeEventSource {

    public List<MotorbikeEvent> get(String motobikeId) {
        Key<Motorbike> motorbike = Key.create(Motorbike.class, motobikeId);

        return ObjectifyService
                .ofy()
                .load()
                .type(MotorbikeEvent.class)
                .ancestor(motorbike)
                .list();
    }

    public void register(MotorbikeEvent event) {
        ObjectifyService
                .ofy()
                .save()
                .entity(event)
                .now();
    }

}
