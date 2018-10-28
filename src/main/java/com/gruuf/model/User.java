package com.gruuf.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.gruuf.auth.Token;
import com.gruuf.web.GruufAuth;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@ToString(exclude = {"password", "passwordHash"})
@EqualsAndHashCode(of = {"email"})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    public static final String EMAIL = "email";
    public static final User EMPTY = new User();

    @Id
    private String id;
    @Index
    private String email;
    private transient String password;
    private String passwordHash;
    @Index
    private Set<Token> tokens;

    private String firstName;
    private String lastName;
    private UserLocale userLocale;
    private boolean notify;
    @Index
    private String facebookId;

    private Date timestamp;

    private Set<PolicyType> acceptedPolicies;

    public boolean hasAnyToken(Token[] tokens) {
        for (Token token : tokens) {
            if (this.tokens.contains(token)) {
                return true;
            }
        }
        return false;
    }

    public UserLocale getUserLocale() {
        return userLocale != null ? userLocale : UserLocale.EN;
    }

    public String getFullName() {
        if (StringUtils.isBlank(firstName) && StringUtils.isBlank(lastName)) {
            return email;
        }
        return firstName + " " + lastName;
    }

    public User withPasswordHash(String hash) {
        this.passwordHash = hash;
        return this;
    }

    public boolean isPrivacyPolicyAccepted() {
        return acceptedPolicies != null && acceptedPolicies.contains(PolicyType.PRIVACY_POLICY);
    }

    public User withPassword(String password) {
        this.password = password;
        return this;
    }

    public static UserCreator create() {
        return new UserCreator(GruufAuth.generateUUID());
    }

    public static UserCreator clone(User user) {
        return new UserCreator(user.getId())
            .withEmail(user.getEmail())
            .withPasswordHash(user.getPasswordHash())
            .withFirstName(user.getFirstName())
            .withLastName(user.getLastName())
            .withUserLocale(user.getUserLocale())
            .withTokens(user.getTokens())
            .withAcceptedPolicies(user.getAcceptedPolicies())
            .withFacebookId(user.getFacebookId());
    }

    public static class UserCreator {

        private User target;

        private UserCreator(String id) {
            target = new User();
            target.id = id;
            target.userLocale = UserLocale.EN;
            target.notify = true;
            target.timestamp = new Date();
        }

        public UserCreator withEmail(String email) {
            target.email = email;
            return this;
        }

        public UserCreator withPasswordHash(String passwordHash) {
            target.passwordHash = passwordHash;
            return this;
        }

        public UserCreator withToken(Token token) {
            if (target.tokens == null) {
                target.tokens = new HashSet<>();
            }
            if (token != null) {
                target.tokens.add(token);
            }
            return this;
        }

        public UserCreator withTokens(Set<Token> tokens) {
            if (target.tokens == null) {
                target.tokens = new HashSet<>();
            }
            if (tokens != null) {
                target.tokens.addAll(tokens);
            }
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

        public UserCreator withNotify(boolean notify) {
            target.notify = notify;
            return this;
        }

        public UserCreator withFacebookId(String facebookId) {
            target.facebookId = facebookId;
            return this;
        }

        public UserCreator withAcceptedPolicies(Set<PolicyType> acceptedPolicies) {
            target.acceptedPolicies = acceptedPolicies;
            return this;
        }

        public User build() {
            return target;
        }

    }
}
