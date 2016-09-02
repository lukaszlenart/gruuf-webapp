package com.gruuf.web.actions;

import com.gruuf.web.RbwActions;
import com.gruuf.web.RbwAuth;
import com.opensymphony.xwork2.ActionSupport;
import com.gruuf.auth.Anonymous;
import com.gruuf.model.User;
import com.gruuf.services.UserStore;
import com.gruuf.web.interceptors.UserStoreAware;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.interceptor.I18nInterceptor;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

@InterceptorRef("defaultWithMessages")
@Anonymous
public class Login extends ActionSupport implements SessionAware, UserStoreAware {

    private Map<String, Object> session;
    private UserStore userStore;

    private String email;
    private String password;

    public String execute() {
        return SUCCESS;
    }

    @Action(value = "login-submit")
    public String submit() {
        User user = userStore.getByEmail(email);
        if (user != null && RbwAuth.isPasswordValid(password, user.getPassword())) {
            session.put(RbwAuth.AUTH_TOKEN, user.getId());
            LOG.debug("Sets user's Locale to {}", user.getUserLocale());
            session.put(I18nInterceptor.DEFAULT_SESSION_ATTRIBUTE, user.getUserLocale().toLocale());

            return RbwActions.HOME;
        } else {
            addActionError("Cannot login!");
            return RbwActions.LOGIN;
        }
    }

    @Action(value = "logout")
    public String logout() {
        session.put(RbwAuth.AUTH_TOKEN, null);
        return RbwActions.LOGIN;
    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    @Override
    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
