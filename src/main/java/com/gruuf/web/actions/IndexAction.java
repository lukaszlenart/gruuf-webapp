package com.gruuf.web.actions;

import com.gruuf.auth.Anonymous;
import com.gruuf.web.GruufActions;

@Anonymous
public class IndexAction extends BaseAction {

    public String execute() {
        return GruufActions.GARAGE;
    }

}
