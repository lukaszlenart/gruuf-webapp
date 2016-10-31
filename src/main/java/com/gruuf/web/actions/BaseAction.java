package com.gruuf.web.actions;

import com.gruuf.auth.Token;
import com.gruuf.model.User;
import com.gruuf.web.interceptors.CurrentUserAware;
import com.opensymphony.xwork2.ActionSupport;

abstract public class BaseAction extends ActionSupport implements CurrentUserAware {

    protected User currentUser;

    @Override
    public void setUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isAdmin() {
        return currentUser.getTokens().contains(Token.ADMIN);
    }

    public String getUserLanguage() {
        return currentUser.getUserLocale().toString().toLowerCase();
    }

    public String getUserDateFormat() {
        return currentUser.getUserLocale().getDateFormat();
    }

    public String getUserDatePickerFormat() {
        return currentUser.getUserLocale().getDatePickerFormat();
    }
}
