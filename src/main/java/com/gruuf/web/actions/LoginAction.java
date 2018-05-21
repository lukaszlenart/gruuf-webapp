package com.gruuf.web.actions;

import com.gruuf.auth.Anonymous;
import com.gruuf.model.User;
import com.gruuf.web.GlobalResult;
import com.gruuf.web.GruufAuth;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;

import static com.opensymphony.xwork2.Action.INPUT;

@InterceptorRef("defaultWithMessages")
@Result(name = INPUT, location = "login")
@Anonymous
public class LoginAction extends BaseLoginAction {

    private String email;
    private String password;

    @SkipValidation
    public String execute() {
        return SUCCESS;
    }

    @Action(value = "login-submit")
    public String submit() {
        User user = userStore.login(email, password);
        if (user != null) {
            markSessionAsLoggedIn(user);
            return GlobalResult.GARAGE;
        } else {
            addActionError("Cannot login!");
            return GlobalResult.LOGIN;
        }
    }

    @Action(value = "logout")
    @SkipValidation
    public String logout() {
        session.put(GruufAuth.AUTH_TOKEN, null);
        return GlobalResult.LOGIN;
    }

    public String getEmail() {
        return email;
    }

    @RequiredStringValidator(key = "general.fieldIsRequired")
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    @RequiredStringValidator(key = "general.fieldIsRequired")
    public void setPassword(String password) {
        this.password = password;
    }

    public String getDemoUserName() {
        return "test@gruuf.com";
    }

    public String getDemoPassword() {
        User demoUser = userStore.findUniqueBy("email", getDemoUserName());

        if (demoUser != null) {
            return demoUser.getPassword();
        }
        return "";
    }
}
