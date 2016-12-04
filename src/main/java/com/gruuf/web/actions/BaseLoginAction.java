package com.gruuf.web.actions;

import com.gruuf.auth.Token;
import com.gruuf.model.User;
import com.gruuf.model.UserLocale;
import com.gruuf.services.MailBox;
import com.gruuf.services.UserStore;
import com.gruuf.web.GruufAuth;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.struts2.interceptor.I18nInterceptor;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class BaseLoginAction extends BaseAction implements SessionAware, ServletRequestAware {

    protected UserStore userStore;
    protected MailBox mailBox;

    protected Map<String, Object> session;
    protected Locale browserLocale;

    protected void markSessionAsLoggedIn(User user) {
        LOG.debug("Logged in user {}", user);
        session.put(GruufAuth.AUTH_TOKEN, user.getId());

        LOG.debug("Sets user's Locale to {}", user.getUserLocale());
        session.put(I18nInterceptor.DEFAULT_SESSION_ATTRIBUTE, user.getUserLocale().toLocale());
    }

    protected User registerAndLogin(String emailAddress, String password, String firstName, String lastName) {
        return registerAndLogin(Collections.singletonList(emailAddress), password, firstName, lastName);
    }

    protected User registerAndLogin(List<String> emailAddress, String password, String firstName, String lastName) {
        User user = finsExistingUser(emailAddress);

        if (user != null) {
            user = updateUser(user, firstName, lastName);
        }

        if (user == null && emailAddress.size() > 0) {
            String email = emailAddress.get(0);
            user = createNewUser(email, password, firstName, lastName);
        }
        return user;
    }

    private User createNewUser(String email, String password, String firstName, String lastName) {

        User.UserCreator newUser = User.create()
                .withEmail(email.trim())
                .withPassword(password)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withToken(Token.USER);

        if (UserLocale.isValidLocale(browserLocale)) {
            newUser = newUser.withUserLocale(UserLocale.fromLocale(browserLocale));
        }

        if (userStore.countAdmins() == 0) {
            newUser = newUser.withToken(Token.ADMIN);
        }

        User user = userStore.put(newUser.build());
        mailBox.notifyAdmin("New biker", "New biker has been registered!", user);

        return user;
    }

    private User updateUser(User user, String firstName, String lastName) {
        if (user.getFirstName() == null || user.getLastName() == null) {
            user = User.clone(user)
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .build();
            user = userStore.put(user);
        }
        return user;
    }

    private User finsExistingUser(List<String> emailAddresses) {
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

    @Inject
    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    @Inject
    public void setMailBox(MailBox mailBox) {
        this.mailBox = mailBox;
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
