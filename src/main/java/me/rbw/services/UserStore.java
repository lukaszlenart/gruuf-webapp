package me.rbw.services;

import com.googlecode.objectify.ObjectifyService;
import me.rbw.model.User;

public class UserStore {

    public User get(String userId) {
        return ObjectifyService
                .ofy()
                .load()
                .type(User.class)
                .id(userId)
                .now();
    }

    public void put(User newUser) {
        ObjectifyService
                .ofy()
                .save()
                .entity(newUser)
                .now();
    }

    public User getByEmail(String email) {
        return ObjectifyService
                .ofy()
                .load()
                .type(User.class)
                .filter("email", email)
                .limit(1)
                .first()
                .now();
    }
}
