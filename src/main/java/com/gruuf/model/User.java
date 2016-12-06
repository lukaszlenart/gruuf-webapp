package com.gruuf.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.gruuf.auth.Token;
import com.gruuf.web.GruufAuth;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    public static final String EMAIL = "email";

    @Id
    private String id;
    @Index
    private String email;
    private String password;
    @Index
    private Set<Token> tokens;

    private String firstName;
    private String lastName;
    private UserLocale userLocale;
    private Date timestamp;

    private User() {
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<Token> getTokens() {
        return tokens;
    }

    public boolean hasAnyToken(Token[] tokens) {
        for (Token token : tokens) {
            if (this.tokens.contains(token)) {
                return true;
            }
        }
        return false;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserLocale getUserLocale() {
        return userLocale != null ? userLocale : UserLocale.EN;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", tokens=" + tokens +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userLocale=" + userLocale +
                ", timestamp=" + timestamp +
                '}';
    }

    public static UserCreator create() {
        return new UserCreator(GruufAuth.generateUUID());
    }

    public static UserCreator clone(User user) {
        return new UserCreator(user.getId())
                .withEmail(user.getEmail())
                .withPassword(user.getPassword())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withUserLocale(user.getUserLocale())
                .withTokens(user.getTokens());
    }

    public static class UserCreator {

        private User target;

        private UserCreator(String id) {
            target = new User();
            target.id = id;
            target.userLocale = UserLocale.EN;
            target.timestamp = new Date();
        }

        public UserCreator withEmail(String email) {
            target.email = email;
            return this;
        }

        public UserCreator withPassword(String password) {
            target.password = password;
            return this;
        }

        public UserCreator withToken(Token token) {
            if (target.tokens == null) {
                target.tokens = new HashSet<>();
            }
            target.tokens.add(token);
            return this;
        }

        public UserCreator withTokens(Set<Token> tokens) {
            if (target.tokens == null) {
                target.tokens = new HashSet<>();
            }
            target.tokens.addAll(tokens);
            return this;
        }

        public UserCreator withFirstName(String firstName) {
            target.firstName = firstName;
            return this;
        }

        public UserCreator withLastName(String lastName) {
            target.lastName = lastName;
            return this;
        }

        public UserCreator replaceTokens(Set<Token> tokens) {
            target.tokens = tokens;
            return this;
        }

        public UserCreator withUserLocale(UserLocale locale) {
            target.userLocale = locale;
            return this;
        }

        public User build() {
            return target;
        }
    }
}
