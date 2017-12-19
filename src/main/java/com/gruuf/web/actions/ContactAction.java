package com.gruuf.web.actions;

import com.gruuf.auth.Anonymous;
import com.gruuf.services.MailBox;
import com.gruuf.struts2.gae.recaptcha.ReCaptchaAware;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;

import static com.opensymphony.xwork2.Action.INPUT;

@Anonymous
@Result(name = INPUT, location = "contact")
@InterceptorRef("reCaptchaStack")
public class ContactAction extends BaseAction implements ReCaptchaAware {

    private MailBox mailBox;
    private boolean reCaptchaValid;

    @SkipValidation
    public String execute() {
        if (currentUser != null) {
            email = currentUser.getEmail();
        }
        return INPUT;
    }

    @Action("contact-submit")
    public String contactSubmit() throws Exception {
        if (isLoggedIn() || reCaptchaValid) {
            mailBox.notifyAdmin("Contact from: " + email, message, "email: " + email);
            addActionMessage(getText("contact.messageHasBeenSent"));
        }

        return INPUT;
    }

    public void validateContactSubmit() {
        if (!isLoggedIn() && !reCaptchaValid) {
            addActionError(getText("general.wrongReCaptcha"));
        }
    }

    @Inject
    public void setMailBox(MailBox mailBox) {
        this.mailBox = mailBox;
    }

    private String email;
    private String message;

    public String getEmail() {
        return email;
    }

    @RequiredStringValidator(key = "contact.emailIsRequired")
    @EmailValidator(key = "contact.wrongEmailAddress")
    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    @RequiredStringValidator(key = "contact.messageIsRequired")
    @StringLengthFieldValidator(minLength = "8", key = "contact.minimumMessageLengthIs")
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void setReCaptchaResult(boolean valid) {
        this.reCaptchaValid = valid;
    }

    @Override
    public boolean isReCaptchaEnabled() {
        return !isLoggedIn();
    }

}
