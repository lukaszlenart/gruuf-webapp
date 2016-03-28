package me.rbw.web.actions.biker;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import me.rbw.auth.Anonymous;
import me.rbw.auth.Token;
import me.rbw.model.User;
import me.rbw.services.UserStore;
import me.rbw.web.RbwActions;
import me.rbw.web.interceptors.UserStoreAware;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.interceptor.validation.SkipValidation;

@Anonymous
public class Register extends ActionSupport implements UserStoreAware {

    private UserStore userStore;

    @SkipValidation
    public String execute() {
        return INPUT;
    }

    @Action("register-submit")
    public String registerSubmit() {
        User.UserCreator newUser = User.create().withEmail(email).withPassword(password1).withToken(Token.USER);
        if (userStore.countAdmins() == 0) {
            newUser = newUser.withToken(Token.ADMIN);
        }
        userStore.put(newUser.build());
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
    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }
}
