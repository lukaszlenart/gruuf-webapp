package me.rbw.services;

import me.rbw.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserRegister {

    private Map<String, User> userCache = new HashMap<>();

    public User get(String userId) {
        return userCache.get(userId);
    }

    public void put(User newUser) {
        userCache.put(newUser.getId(), newUser);
    }

}
