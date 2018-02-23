package com.gruuf.web.actions;

import com.gruuf.GruufVersion;
import com.gruuf.auth.Token;
import com.gruuf.model.User;
import com.gruuf.model.UserLocale;
import com.gruuf.web.interceptors.CurrentUserAware;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class BaseAction extends ActionSupport implements CurrentUserAware {

    private static final Logger LOG = LogManager.getLogger(BaseAction.class);

    public static final String JSON = "json";
    public static final String TO_INPUT = "to-input";
    public static final String PRIVACY_POLICY = "privacy-policy";

    protected User currentUser;

    @Override
    public void setUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isAdmin() {
        return currentUser != null && currentUser.getTokens().contains(Token.ADMIN);
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public String getUserLanguage() {
        return getCurrentUserLocale().toString().toLowerCase();
    }

    public String getUserDateFormat() {
        return currentUser.getUserLocale().getDateFormat();
    }

    public String getUserDatePickerFormat() {
        return currentUser.getUserLocale().getDatePickerFormat();
    }

    public String getFullName() {
        return currentUser != null ? currentUser.getFullName() : (isLoggedIn() ? getText("biker.profile") : null);
    }

    public String getCurrentVersion() {
        LOG.debug("Current version: {}", GruufVersion.CURRENT_VERSION);
        return GruufVersion.CURRENT_VERSION;
    }

    public UserLocale getCurrentUserLocale() {
        if (currentUser == null) {
            return UserLocale.EN;
        }
        return currentUser.getUserLocale();
    }

    public Set<UserLocale> getAvailableUserLocales() {
        return UserLocale.all();
    }
}
