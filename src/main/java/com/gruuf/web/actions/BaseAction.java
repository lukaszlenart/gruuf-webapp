package com.gruuf.web.actions;

import com.gruuf.GruufVersion;
import com.gruuf.auth.Token;
import com.gruuf.model.User;
import com.gruuf.model.UserLocale;
import com.gruuf.services.MailBox;
import com.gruuf.web.interceptors.CurrentUserAware;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.I18nInterceptor;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Currency;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class BaseAction extends ActionSupport implements CurrentUserAware, SessionAware {

    private static final Logger LOG = LogManager.getLogger(BaseAction.class);

    public static final String JSON = "json";
    public static final String TO_INPUT = "to-input";

    @Inject
    protected MailBox mailBox;

    protected User currentUser;
    protected UserLocale currentUserLocale = UserLocale.EN;
    protected Currency userCurrency = Currency.getInstance(Locale.US);

    protected Map<String, Object> session;

    @Override
    public void withUser(User currentUser) {
        this.currentUser = currentUser;

        if (currentUser == null) {
            this.currentUserLocale = UserLocale.EN;
        } else {
            this.currentUserLocale = currentUser.getUserLocale();
            this.userCurrency = Currency.getInstance(currentUserLocale.toLocale());
        }

        switchLocale(this.currentUserLocale);
    }

    protected void switchLocale(UserLocale userLocale){
        LOG.debug("Sets user's Locale to {}", userLocale);
        session.put(I18nInterceptor.DEFAULT_SESSION_ATTRIBUTE, userLocale.toLocale());
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public boolean isAdmin() {
        return currentUser != null
            && currentUser.getTokens() != null
            && currentUser.getTokens().contains(Token.ADMIN);
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
        return currentUserLocale;
    }

    public String getUserCurrency() {
        return userCurrency.getCurrencyCode();
    }

    public Set<UserLocale> getAvailableUserLocales() {
        return UserLocale.all();
    }

    protected void sendNewPassword(User user) {
        if (user != null) {
            String subject = getText("user.passwordReset");
            String body = getText("user.yourNewPasswordIs") + " " + user.getPassword() + "\n";
            mailBox.notifyUser(user, subject, body);
            addActionMessage(getText("user.passwordHasBeenChanged"));
        }
    }
}
