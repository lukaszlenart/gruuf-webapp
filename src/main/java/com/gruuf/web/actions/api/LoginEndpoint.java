package com.gruuf.web.actions.api;

import com.gruuf.auth.Anonymous;
import com.gruuf.model.User;
import com.gruuf.services.UserStore;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;

import static com.gruuf.web.actions.BaseAction.JSON;
import static com.opensymphony.xwork2.Action.SUCCESS;

@Result(name = SUCCESS, type = JSON, params = {"root", "result"})
@InterceptorRefs({
        @InterceptorRef("gruufDefaultDev"),
        @InterceptorRef("json")
})
@Anonymous
public class LoginEndpoint extends BaseAction {

    private static final Logger LOG = LogManager.getLogger(LoginEndpoint.class);

    private UserStore userStore;

    private LoginResult result;
    private String username;
    private String password;

    public String execute() {
        if (StringUtils.isEmpty(username) && StringUtils.isEmpty(password)) {
            LOG.debug("Username and password are empty!");
            result = new LoginResult().failed();
        } else if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            LOG.debug("Username {} or password is empty", username);
            result = new LoginResult().failed();
        } else {
            User user = userStore.login(username, password);
            if (user == null) {
                LOG.debug("Cannot login username {}", username);
                result = new LoginResult().failed();
            } else {
                LOG.debug("User {} logged in!", username);
                result = new LoginResult(user).withToken(user.getId()).ok();
            }
        }
        return SUCCESS;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginResult getResult() {
        return result;
    }

    @Inject
    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    public static class LoginResult {
        private final User user;
        private String status;
        private String token;

        public LoginResult() {
            user = null;
        }

        public LoginResult(User user) {
            this.user = user;
        }

        public String getFullName() {
            return user == null ? null : user.getFullName();            
        }

        public String getFirstName() {
            return user == null ? null : user.getFirstName();
        }

        public String getStatus() {
            return status;
        }

        public String getToken() {
            return token;
        }

        public LoginResult ok() {
            this.status = "ok";
            return this;
        }

        public LoginResult failed() {
            this.status = "failed";
            return this;
        }

        public LoginResult withToken(String token) {
            this.token = token;
            return this;
        }
    }

}
