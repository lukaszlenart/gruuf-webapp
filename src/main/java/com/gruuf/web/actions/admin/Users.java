package com.gruuf.web.actions.admin;

import com.gruuf.auth.Token;
import com.gruuf.web.RbwActions;
import com.opensymphony.xwork2.ActionSupport;
import com.gruuf.auth.Tokens;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Tokens(Token.ADMIN)
public class Users extends ActionSupport {

    private static Logger LOG = LogManager.getLogger(Users.class);

    public String execute() {
        return RbwActions.ADMIN_USERS;
    }

}
