package com.gruuf.web.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.gruuf.auth.Anonymous;
import com.gruuf.web.RbwActions;

@Anonymous
public class Index extends ActionSupport {

    public String execute() {
        return RbwActions.HOME;
    }

}
