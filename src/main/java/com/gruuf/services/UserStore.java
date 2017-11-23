package com.gruuf.services;

import com.gruuf.auth.Token;
import com.gruuf.model.User;
import com.gruuf.web.GruufAuth;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

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

    public User login(String username, String password) {
        User user = findUniqueBy("email", username.trim().toLowerCase());
        if (user != null && GruufAuth.isPasswordValid(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public User resetPassword(String email) {
        User user = findUniqueBy("email", email);
        if (user != null) {
            user = User.clone(user).withPassword(GruufAuth.randomString()).build();
            user = put(user);
        }
        return user;
    }
}