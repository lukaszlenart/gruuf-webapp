package com.gruuf.web.actions.admin;

import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.model.User;
import com.gruuf.services.UserStore;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Tokens(Token.ADMIN)
public class UsersAction extends BaseAction {

    private static Logger LOG = LogManager.getLogger(UsersAction.class);
    private UserStore userStore;

    public String execute() {
        return "users";
    }

    public List<User> getList() {
        List<User> userList = userStore.list();
        LOG.debug("Found users {}", userList);
        return userList;
    }

    @Inject
    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }
}
