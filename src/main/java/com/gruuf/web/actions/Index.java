package com.gruuf.web.actions;

import com.gruuf.auth.Anonymous;
import com.gruuf.web.RbwActions;

@Anonymous
public class Index extends BaseAction {

    public String execute() {
        return RbwActions.HOME;
    }

}
