package com.gruuf.web.actions;

import com.gruuf.auth.Token;
import com.gruuf.model.User;
import com.gruuf.model.UserLocale;
import com.gruuf.web.interceptors.CurrentUserAware;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements CurrentUserAware {

    protected User currentUser;

    @Override
    public void setUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isAdmin() {
        return currentUser != null && currentUser.getTokens().contains(Token.ADMIN);
    }

    public boolean isLoggedIn() {
        return currentUser != null ;
    }

    public String getUserLanguage() {
        if (currentUser != null) {
            return currentUser.getUserLocale().toString().toLowerCase();
        } else {
            return UserLocale.EN.toString().toLowerCase();
        }
    }

    public String getUserDateFormat() {
        return currentUser.getUserLocale().getDateFormat();
    }

    public String getUserDatePickerFormat() {
        return currentUser.getUserLocale().getDatePickerFormat();
    }
}
