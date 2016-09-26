package com.gruuf.web.actions.biker;

import com.gruuf.auth.Anonymous;
import com.gruuf.auth.Token;
import com.gruuf.model.User;
import com.gruuf.model.UserLocale;
import com.gruuf.services.MailBox;
import com.gruuf.services.UserStore;
import com.gruuf.web.GruufActions;
import com.gruuf.web.actions.BaseAction;
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
public class RegisterAction extends BaseAction implements ServletRequestAware {

    private UserStore userStore;
    private MailBox mailBox;
    private Locale browserLocale;

    @SkipValidation
    public String execute() {
        return INPUT;
    }

    @Action("register-submit")
    public String registerSubmit() {
        User.UserCreator newUser = User.create().withEmail(email.trim()).withPassword(password1).withToken(Token.USER);

        if (UserLocale.isValidLocale(browserLocale)) {
            newUser = newUser.withUserLocale(UserLocale.fromLocale(browserLocale));
        }

        if (userStore.countAdmins() == 0) {
            newUser = newUser.withToken(Token.ADMIN);
        }

        User user = userStore.put(newUser.build());
        mailBox.notifyAdmin("New biker", "New biker has been registered!", user);

        return GruufActions.LOGIN;
    }

    public void validateRegisterSubmit() {
        if (!password1.equals(password2)) {
            addFieldError("password1", "Passwords don't match!");
        }

        if(userStore.findBy("email", email.trim()).size() > 0) {
            addFieldError("email", "E-mail address is already registered!");
        }
    }

    @Inject
    public void setMailBox(MailBox mailBox) {
        this.mailBox = mailBox;
    }

    @Inject
    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        browserLocale = request.getLocale();
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

}
