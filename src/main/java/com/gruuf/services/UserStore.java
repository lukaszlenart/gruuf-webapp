package com.gruuf.services;

import com.gruuf.auth.Token;
import com.gruuf.model.User;

import java.util.Collections;
import java.util.List;

public class UserStore extends Storable<User> {

    public UserStore(Class<User> type) {
        super(type);
    }

    public int countAdmins() {
        return filter("tokens in", Collections.singletonList(Token.ADMIN))
                .count();
    }

    public List<User> list() {
        return list("email");
    }

    public List<User> listAdmins() {
        return filter("tokens in", Collections.singletonList(Token.ADMIN))
                .list();
    }

}