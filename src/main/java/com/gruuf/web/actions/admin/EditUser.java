package com.gruuf.web.actions.admin;

import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.model.User;
import com.gruuf.model.UserLocale;
import com.gruuf.services.UserStore;
import com.gruuf.web.interceptors.UserStoreAware;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;

import java.util.HashSet;
import java.util.Set;

@Tokens(Token.ADMIN)
@Result(name = "to-users", location = "users", type = "redirectAction")
public class EditUser extends ActionSupport implements UserStoreAware {

    private static Logger LOG = LogManager.getLogger(EditUser.class);

    private UserStore userStore;

    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private UserLocale userLocale;
    private Set<Token> tokens;

    @SkipValidation
    public String execute() {
        LOG.debug("Showing user edit form");

        User user = userStore.get(userId);
        userId = user.getId();
        email = user.getEmail();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        userLocale = user.getUserLocale();
        tokens = user.getTokens();

        return INPUT;
    }

    @Action("update-user")
    public String updateUser() {
        LOG.debug("Updating user with ID {} and firstName = {}, lastName = {} and tokens = {}", userId, firstName, lastName, tokens);

        if (tokens == null) {
            tokens = new HashSet<>();
        }
        if (tokens.size() == 0) {
            tokens.add(Token.USER);
        }

        User updatedUser = User.clone(userStore.get(userId))
                .replaceTokens(tokens)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withUserLocale(userLocale)
                .build();

        LOG.debug("Storing updated user {}", updatedUser);
        userStore.put(updatedUser);

        if (userStore.countAdmins() == 0) {
            LOG.debug("No more admins, adding ADMIN token to user {}", userId);

            tokens.add(Token.ADMIN);
            updatedUser = User.clone(userStore.get(userId))
                    .withTokens(tokens)
                    .build();

            userStore.put(updatedUser);
        }

        return "to-users";
    }

    @Override
    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Token> getTokens() {
        return tokens;
    }

    public void setTokens(Set<Token> tokens) {
        this.tokens = tokens;
    }

    public UserLocale getUserLocale() {
        return userLocale;
    }

    public void setUserLocale(UserLocale userLocale) {
        this.userLocale = userLocale;
    }

    public Set<Token> getAvailableTokens() {
        return Token.all();
    }

    public Set<UserLocale> getAvailableUserLocales() {
        return UserLocale.all();
    }
}