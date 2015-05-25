package me.rbw.web.actions;

import com.opensymphony.xwork2.ActionSupport;
import me.rbw.web.RbwAuth;
import me.rbw.web.RbwActions;
import me.rbw.web.interceptors.UserAware;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class Login extends ActionSupport implements SessionAware {

    private Map<String, Object> session;

    public String execute() {
        return SUCCESS;
    }

    @Action(value = "login-submit")
    public String submit() {
        session.put(RbwAuth.AUTH_TOKEN, "logged");
        return RbwActions.HOME;
    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
