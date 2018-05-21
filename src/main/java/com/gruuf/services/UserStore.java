package com.gruuf.services;

import com.gruuf.GruufConstants;
import com.gruuf.auth.Token;
import com.gruuf.model.User;
import com.gruuf.web.GruufAuth;
import com.opensymphony.xwork2.inject.Inject;

import java.util.Collections;
import java.util.List;

public class UserStore extends Storable<User> {

    @Inject(GruufConstants.SECURITY_SALT)
    private String applicationSalt;

    public UserStore() {
        super(User.class);
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
        String passwordHash = GruufAuth.hash(password, applicationSalt);

        if (user != null && passwordHash.equals(user.getPasswordHash())) {
            return user;
        }
        return null;
    }

    public User resetPassword(String email) {
        User user = findUniqueBy("email", email);
        if (user != null) {
            user = resetPassword(user);
        }
        return user;
    }

    public User resetPassword(User user) {
        String newPassword = GruufAuth.randomString();
        return resetPassword(user, newPassword);
    }

    public User resetPassword(User user, String password) {
        String passwordHash = GruufAuth.hash(password, applicationSalt);
        user = User.clone(user).build().withPasswordHash(passwordHash);
        user = put(user);
        user = user.withPassword(password);
        return user;
    }

    public User findByEmail(String emailAddress) {
        return findUniqueBy("email", emailAddress);
    }

}