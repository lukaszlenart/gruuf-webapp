package me.rbw.services;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import me.rbw.model.Bike;
import me.rbw.model.User;

import java.util.List;

public class Garage {

    public List<Bike> get(String userId) {
        Key<User> user = Key.create(User.class, userId);

        return ObjectifyService
                .ofy()
                .load()
                .type(Bike.class)
                .ancestor(user)
                .list();
    }

    public void put(Bike bike) {
        ObjectifyService
                .ofy()
                .save()
                .entity(bike)
                .now();
    }
}
