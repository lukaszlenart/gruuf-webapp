package com.gruuf.web.actions.admin;

import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.model.User;
import com.gruuf.services.UserStore;
import com.gruuf.web.GruufActions;
import com.gruuf.web.actions.BaseAction;
import com.gruuf.web.interceptors.UserStoreAware;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.interceptor.validation.SkipValidation;

@Tokens(Token.ADMIN)
public class PasswordReset extends BaseAction implements UserStoreAware {

    private static Logger LOG = LogManager.getLogger(PasswordReset.class);

    private UserStore userStore;

    private String userId;
    private String oldPassword;
    private String newPassword1;
    private String newPassword2;

    @SkipValidation
    public String execute() {
        LOG.debug("Showing password reset form");
        return INPUT;
    }

    @Action("update-password")
    public String updatePassword() {
        User user = userStore.get(userId);

        LOG.debug("Updating user {} with new password {}", userId, newPassword1);

        User updatedUser = User.clone(user).withPassword(newPassword1).build();
        userStore.put(updatedUser);
        addActionMessage("Password updated successfully!");
        return GruufActions.GARAGE;
    }

    public void validateUpdatePassword() {
        if (!newPassword1.equals(newPassword2)) {
            addFieldError("newPassword1", "Passwords don't match!");
        }

        User user = userStore.get(userId);
        if (user == null) {
            LOG.error("Cannot find user with id {} to reset password!", userId);
            addActionError("No user with such ID %{userId}!");
        } else {
            if (!user.getPassword().equals(oldPassword)) {
                addFieldError("oldPassword", "Password doesn't match existing password!");
            }
        }
    }

    public User getUser() {
        User user = userStore.get(userId);
        if (user == null) {
            LOG.error("Cannot find user with id {}", userId);
        } else {
            LOG.debug("Found user {}", user);
        }
        return user;
    }

    @Override
    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    @RequiredStringValidator(message = "Old password is required!")
    @StringLengthFieldValidator(minLength = "8", message = "Min password length is %{minLength}")
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword1() {
        return newPassword1;
    }

    @RequiredStringValidator(message = "New password is required!")
    @StringLengthFieldValidator(minLength = "8", message = "Min password length is %{minLength}")
    public void setNewPassword1(String newPassword1) {
        this.newPassword1 = newPassword1;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    @RequiredStringValidator(message = "Repeat password!")
    @StringLengthFieldValidator(minLength = "8", message = "Min password length is %{minLength}")
    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }
}
