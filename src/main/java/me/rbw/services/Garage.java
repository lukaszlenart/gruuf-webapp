package me.rbw.services;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import me.rbw.model.Motorbike;
import me.rbw.model.User;

import java.util.List;

public class Garage {

    public List<Motorbike> get(String userId) {
        Key<User> user = Key.create(User.class, userId);

        return ObjectifyService
                .ofy()
                .load()
                .type(Motorbike.class)
                .ancestor(user)
                .list();
    }

    public void put(Motorbike motorbike) {
        ObjectifyService
                .ofy()
                .save()
                .entity(motorbike)
                .now();
    }
}
