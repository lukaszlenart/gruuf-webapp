package com.gruuf.web.actions.biker;

import com.gruuf.model.User;
import com.gruuf.web.actions.BaseLoginAction;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;

import static com.opensymphony.xwork2.Action.INPUT;

@Result(name = INPUT, location = "biker/profile")
public class ProfileAction extends BaseLoginAction implements Preparable, Validateable {

    private static final Logger LOG = LogManager.getLogger(ProfileAction.class);

    @SkipValidation
    public String execute() {
        return INPUT;
    }

    @Action("profile-submit")
    public String profileSubmit() {
        User.UserCreator user = User.clone(currentUser)
                .withFirstName(firstName)
                .withLastName(lastName);

        if (!currentUser.getEmail().equals(email)) {
            LOG.debug("New email has been defined: {}", email);
            addActionMessage("biker.newEmailHasBeenSet");
            user = user.withEmail(email);
        }

        if (StringUtils.isNotBlank(password1) && !currentUser.getPassword().equals(password1)) {
            LOG.debug("New password has been defined");

            addActionMessage(getText("biker.newPasswordHasBeenSet"));
            user = user.withPassword(password1);
        }

        currentUser = userStore.put(user.build());

        LOG.debug("User profiles has been updated {}", currentUser);

        return INPUT;
    }

    public void validateProfileSubmit() {
        if (!currentUser.getEmail().equals(email)) {
            if (userStore.findBy("email", email.trim()).size() > 0) {
                email = currentUser.getEmail();
                addFieldError("email", getText("biker.emailAddressAlreadyRegistered"));
            }
        }

        if ((StringUtils.isNotBlank(password1) && !currentUser.getPassword().equals(password1))
                ||
            (StringUtils.isNotBlank(password2) && !currentUser.getPassword().equals(password2)))
        {
            if (!password1.equals(password2)) {
                addFieldError("password1", getText("biker.passwordDoNotMatch"));
            }
        }
    }

    @Override
    public void prepare() throws Exception {
        email = currentUser.getEmail();
        firstName = currentUser.getFirstName();
        lastName = currentUser.getLastName();
    }

    private String email;
    private String firstName;
    private String lastName;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

}
