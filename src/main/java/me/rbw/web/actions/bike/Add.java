package me.rbw.web.actions.bike;

import com.opensymphony.xwork2.ActionSupport;
import me.rbw.web.RbwActions;
import org.apache.struts2.convention.annotation.Action;

public class Add extends ActionSupport {

    public String execute() {
        return SUCCESS;
    }

    @Action("add-submit")
    public String addSubmit() {
        return RbwActions.HOME;
    }
}
