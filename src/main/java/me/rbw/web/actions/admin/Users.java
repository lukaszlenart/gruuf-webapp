package me.rbw.web.actions.admin;

import com.opensymphony.xwork2.ActionSupport;
import me.rbw.auth.Token;
import me.rbw.auth.Tokens;
import me.rbw.web.RbwActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Token(Tokens.ADMIN)
public class Users extends ActionSupport {

    private static Logger LOG = LogManager.getLogger(Users.class);

    public String execute() {
        return RbwActions.ADMIN_USERS;
    }

}
