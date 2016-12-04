package com.gruuf.web.actions.biker;

import com.gruuf.auth.Anonymous;
import com.gruuf.auth.Token;
import com.gruuf.model.User;
import com.gruuf.model.UserLocale;
import com.gruuf.services.MailBox;
import com.gruuf.services.UserStore;
import com.gruuf.web.GruufActions;
import com.gruuf.web.actions.BaseAction;
import com.gruuf.web.actions.BaseLoginAction;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Anonymous
public class RegisterAction extends BaseLoginAction {

    @SkipValidation
    public String execute() {
        return INPUT;
    }

    @Action("register-submit")
    public String registerSubmit() {
        registerAndLogin(email, password1, null, null);
        return GruufActions.GARAGE;
    }

    public void validateRegisterSubmit() {
        if (!password1.equals(password2)) {
            addFieldError("password1", getText("biker.passwordDoNotMatch"));
        }

        if(userStore.findBy("email", email.trim()).size() > 0) {
            addFieldError("email", getText("biker.emailAddressAlreadyRegistered"));
        }
    }

    private String email;
    private String password1;
    private String password2;

    public String getEmail() {
        return email;
    }

    @RequiredStringValidator(key = "biker.emailIsRequired")
    @EmailValidator(key = "biker.wrongEmail")
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword1() {
        return password1;
    }

    @RequiredStringValidator(key = "biker.passwordIsRequired")
    @StringLengthFieldValidator(minLength = "8", key = "biker.minimumPasswordLengthIs")
    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    @RequiredStringValidator(key = "biker.repeatPassword")
    @StringLengthFieldValidator(minLength = "8", key = "biker.minimumPasswordLengthIs")
    public void setPassword2(String password2) {
        this.password2 = password2;
    }

}
