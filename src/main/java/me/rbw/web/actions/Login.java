package me.rbw.web.actions;

import com.opensymphony.xwork2.ActionSupport;
import me.rbw.auth.Anonymous;
import me.rbw.model.User;
import me.rbw.services.UserStore;
import me.rbw.web.RbwAuth;
import me.rbw.web.RbwActions;
import me.rbw.web.interceptors.UserStoreAware;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
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
            return RbwActions.HOME;
        } else {
            addActionError("Cannot login!");
            return RbwActions.LOGIN;
        }
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
