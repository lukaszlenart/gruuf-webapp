package me.rbw.services;

import me.rbw.model.User;

public class UserRegister {

    public User get(String userId) {
        return new User(userId, "lukasz@lenart", "Lukasz", "Lenart");
    }

}
