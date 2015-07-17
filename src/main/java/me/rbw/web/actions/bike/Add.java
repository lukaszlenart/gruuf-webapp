package me.rbw.web.actions.bike;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import me.rbw.web.RbwActions;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.interceptor.validation.SkipValidation;

public class Add extends ActionSupport {

    @SkipValidation
    public String execute() {
        return INPUT;
    }

    @Action("add-submit")
    public String addSubmit() {
        return RbwActions.HOME;
    }

    private String friendlyName;
    private String vin;

    public String getFriendlyName() {
        return friendlyName;
    }

    @RequiredStringValidator(message = "'Friendly name' is required!")
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getVin() {
        return vin;
    }

    @RequiredStringValidator(message = "'VIN' is required!")
    public void setVin(String vin) {
        this.vin = vin;
    }
}
