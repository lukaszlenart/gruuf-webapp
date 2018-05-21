package com.gruuf.web.actions;

import com.gruuf.auth.Anonymous;
import com.gruuf.model.User;
import com.gruuf.web.GlobalResult;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;

import static com.opensymphony.xwork2.Action.INPUT;

@InterceptorRef("defaultWithMessages")
@Result(name = INPUT, location = "reset-input")
@Anonymous
public class ResetPasswordAction extends BaseLoginAction {

    private String email;

    @SkipValidation
    public String execute() {
        return INPUT;
    }

    @Action(value = "reset-submit")
    public String reset() {
        User user = userStore.resetPassword(email);
        sendNewPassword(user);
        return GlobalResult.LOGIN;
    }

    public String getEmail() {
        return email;
    }

    @RequiredStringValidator(key = "general.fieldIsRequired")
    public void setEmail(String email) {
        this.email = email;
    }

}
