package me.rbw.web.actions.biker;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import me.rbw.model.User;
import me.rbw.web.RbwActions;
import me.rbw.web.interceptors.RegisterUserAware;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.interceptor.validation.SkipValidation;

public class Register extends ActionSupport implements RegisterUserAware {

    @SkipValidation
    public String execute() {
        return INPUT;
    }

    @Action("register-submit")
    public String registerSubmit() {
        return RbwActions.LOGIN;
    }

    public void validateRegisterSubmit() {
        if (!password1.equals(password2)) {
            addFieldError("password1", "Passwords don't match!");
        }
    }

    private String email;
    private String password1;
    private String password2;

    public String getEmail() {
        return email;
    }

    @RequiredStringValidator(message = "Email is required!")
    @EmailValidator(message = "Wrong email!")
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword1() {
        return password1;
    }

    @RequiredStringValidator(message = "Password is required!")
    @StringLengthFieldValidator(minLength = "8", message = "Min password length is %{minLength}")
    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    @RequiredStringValidator(message = "Repeat password!")
    @StringLengthFieldValidator(minLength = "8", message = "Min password length is %{minLength}")
    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    @Override
    public User registerUser() {
        return User.create().withEmail(email).withPassword(password1).build();
    }

}
