package me.rbw.web.actions;

import com.opensymphony.xwork2.ActionSupport;
import me.rbw.auth.Anonymous;
import me.rbw.web.RbwActions;

@Anonymous
public class Index extends ActionSupport {

    public String execute() {
        return RbwActions.HOME;
    }

}
