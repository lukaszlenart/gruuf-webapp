package me.rbw.model;

import me.rbw.web.RbwAuth;

public class User {

    private String id;
    private String email;
    private String password;

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

    public static UserCreator create() {
        return new UserCreator(RbwAuth.generateUUID());
    }

    public static class UserCreator {

        private User target;

        public UserCreator(String id) {
            target = new User();
            target.id = id;
        }

        public UserCreator withEmail(String email) {
            target.email = email;
            return this;
        }

        public UserCreator withPassword(String password) {
            target.password = password;
            return this;
        }

        public User build() {
            return target;
        }
    }
}
