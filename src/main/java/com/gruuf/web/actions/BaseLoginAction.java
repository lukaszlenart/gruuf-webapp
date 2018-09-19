package com.gruuf.web.actions;

import com.gruuf.auth.Token;
import com.gruuf.model.User;
import com.gruuf.model.UserLocale;
import com.gruuf.services.UserStore;
import com.gruuf.web.GruufAuth;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class BaseLoginAction extends BaseAction implements ServletRequestAware {

    private static final Logger LOG = LogManager.getLogger(BaseLoginAction.class);

    @Inject
    protected UserStore userStore;

    protected Locale browserLocale;

    protected void markSessionAsLoggedIn(User user) {
        LOG.debug("Logged in user {}", user);
        session.put(GruufAuth.AUTH_TOKEN, user.getId());

        switchLocale(user.getUserLocale());
    }

    protected User registerAndLogin(String emailAddress, String password) {
        return registerAndLogin(Collections.singletonList(emailAddress), password, null, null, null);
    }

    protected User registerAndLogin(List<String> emailAddresses, String password, String firstName, String lastName, String facebookId) {
        User user = findExistingUser(emailAddresses);

        if (user != null) {
            user = updateUser(user, firstName, lastName, facebookId);
        }

        if (user == null && emailAddresses.size() > 0) {
            String email = emailAddresses.get(0);
            user = createNewUser(email, password, firstName, lastName, facebookId);
        }
        return user;
    }

    private User createNewUser(String email, String password, String firstName, String lastName, String facebookId) {

        User.UserCreator newUser = User.create()
            .withEmail(email.trim())
            .withFirstName(firstName)
            .withLastName(lastName)
            .withFacebookId(facebookId)
            .withToken(Token.USER);

        if (UserLocale.isValidLocale(browserLocale)) {
            newUser = newUser.withUserLocale(UserLocale.fromLocale(browserLocale));
        }

        if (userStore.countAdmins() == 0) {
            newUser = newUser.withToken(Token.ADMIN);
        }

        User user;
        if (StringUtils.isBlank(password)) {
            user = userStore.put(newUser.build());
        } else {
            user = userStore.resetPassword(newUser.build(), password);
        }

        mailBox.notifyAdmin("New biker", "New biker has been registered!", user);

        return user;
    }

    private User updateUser(User user, String firstName, String lastName, String facebookId) {
        User.UserCreator clone = User.clone(user);

        if (user.getFirstName() == null || user.getLastName() == null) {
            clone = clone.withFirstName(firstName).withLastName(lastName);
        }
        if (StringUtils.isNoneEmpty(facebookId)) {
            clone = clone.withFacebookId(facebookId);
        }

        return userStore.put(clone.build());
    }

    private User findExistingUser(List<String> emailAddresses) {
        User user = null;

        for (String email : emailAddresses) {
            user = userStore.findUniqueBy("email", email.trim());
            if (user != null) {
                LOG.debug("Found matching existing user with email {}", email);
                break;
            }
        }
        return user;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        browserLocale = request.getLocale();
    }
}
