package me.rbw.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import me.rbw.auth.Token;
import me.rbw.web.RbwAuth;

import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @Id
    private String id;
    @Index
    private String email;
    private String password;
    @Index
    private Set<Token> tokens;

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

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", tokens=" + tokens +
                '}';
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

        public UserCreator withToken(Token token) {
            if (target.tokens == null) {
                target.tokens = new HashSet<>();
            }
            target.tokens.add(token);
            return this;
        }

        public User build() {
            return target;
        }
    }
}
