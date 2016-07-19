package com.gruuf.services;

import com.googlecode.objectify.ObjectifyService;
import com.gruuf.auth.Token;
import com.gruuf.model.User;

import java.util.Collections;
import java.util.List;

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

    public int countAdmins() {
        return ObjectifyService
                .ofy()
                .load()
                .type(User.class)
                .filter("tokens in", Collections.singletonList(Token.ADMIN))
                .count();
    }

    public List<User> list() {
        return ObjectifyService
                .ofy()
                .load()
                .type(User.class)
                .order("email")
                .list();
    }
}
