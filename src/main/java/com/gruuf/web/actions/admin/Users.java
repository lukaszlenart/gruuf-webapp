package com.gruuf.web.actions.admin;

import com.gruuf.auth.Token;
import com.gruuf.model.User;
import com.gruuf.services.UserStore;
import com.gruuf.web.RbwActions;
import com.gruuf.web.actions.BaseAction;
import com.gruuf.web.interceptors.UserStoreAware;
import com.gruuf.auth.Tokens;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Tokens(Token.ADMIN)
public class Users extends BaseAction implements UserStoreAware {

    private static Logger LOG = LogManager.getLogger(Users.class);
    private UserStore userStore;

    public String execute() {
        return RbwActions.ADMIN_USERS;
    }

    public List<User> getList() {
        List<User> userList = userStore.list();
        LOG.debug("Found users {}", userList);
        return userList;
    }

    @Override
    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }
}
