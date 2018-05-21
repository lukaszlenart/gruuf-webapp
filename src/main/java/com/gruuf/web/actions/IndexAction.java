package com.gruuf.web.actions;

import com.gruuf.auth.Anonymous;
import com.gruuf.web.GlobalResult;

@Anonymous
public class IndexAction extends BaseAction {

    public String execute() {
        return GlobalResult.GARAGE;
    }

}
