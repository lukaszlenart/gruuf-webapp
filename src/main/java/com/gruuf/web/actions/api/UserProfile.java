package com.gruuf.web.actions.api;

import com.gruuf.model.User;

public class UserProfile {

    private final String firstName;
    private final String lastName;
    private final String email;

    public UserProfile(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
