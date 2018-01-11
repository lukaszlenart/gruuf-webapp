package com.gruuf.web.actions;

import com.gruuf.auth.Anonymous;
import com.gruuf.model.User;
import com.gruuf.web.GruufActions;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.Reading;
import facebook4j.auth.AccessToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;

import java.util.Collections;

import static com.opensymphony.xwork2.Action.SUCCESS;

@Anonymous
@InterceptorRefs({
        @InterceptorRef("defaultWithMessages"),
        @InterceptorRef("json")
})
@Result(name = SUCCESS, type = "jsonRedirect", params = {"actionName", "%{redirect}"})
public class FacebookLoginAction extends BaseLoginAction {

    private static final Logger LOG = LogManager.getLogger(FacebookLoginAction.class);

    private String accessToken;
    private String redirect = GruufActions.LOGIN;

    @Action("facebook-login")
    public Object facebookLogin() throws Exception {

        if (StringUtils.isEmpty(accessToken)) {
            LOG.warn("Facebook accessToken is empty, redirecting back to login page");
            redirect = GruufActions.LOGIN;
        } else {
            Facebook facebook = new FacebookFactory().getInstance(new AccessToken(accessToken, null));

            facebook4j.User facebookMe = facebook.getMe(new Reading().fields("email", "first_name", "last_name"));

            String facebookId = facebookMe.getId();
            String emailAddress = facebookMe.getEmail();
            String lastName = facebookMe.getLastName();
            String firstName = facebookMe.getFirstName();

            LOG.debug("Got {} {} {} with emails {}", facebookId, firstName, lastName, emailAddress);

            User user = registerAndLogin(Collections.singletonList(emailAddress), null, firstName, lastName);

            if (user != null) {
                markSessionAsLoggedIn(user);
                addActionMessage(getText("user.loggedInWithFacebookAccount", new String[]{user.getFullName(), user.getEmail()}));
                redirect = GruufActions.GARAGE;
            } else {
                LOG.debug("User is null, cannot login!");
                addActionError(getText("user.cannotLoginWithFacebookAccount"));
                redirect = GruufActions.LOGIN;
            }
        }

        return SUCCESS;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRedirect() {
        return redirect;
    }

}
