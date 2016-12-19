package com.gruuf.web.actions;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.gruuf.GruufConstants;
import com.gruuf.auth.Anonymous;
import com.gruuf.model.User;
import com.gruuf.web.GruufActions;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.commons.lang3.StringUtils;
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
@Result(name = SUCCESS, type = "jsonRedirect", params = { "actionName", "%{redirect}" })
public class GoogleLoginAction extends BaseLoginAction {

    public static final String TOKEN_URL = "https://www.googleapis.com/oauth2/v4/token";

    private String googleApiKey;
    private String googleApiSecret;
    private String hostUrl;

    private String code;
    private String redirect = GruufActions.LOGIN;

    @Action("google-login")
    public Object googleLogin() throws Exception {

        if (StringUtils.isEmpty(code)) {
            LOG.warn("Google auth code is empty, redirecting back to login page");
            redirect = GruufActions.LOGIN;
        }

        GoogleTokenResponse response =
                new GoogleAuthorizationCodeTokenRequest(
                        new NetHttpTransport(),
                        JacksonFactory.getDefaultInstance(),
                        TOKEN_URL,
                        googleApiKey,
                        googleApiSecret,
                        code,
                        hostUrl)
                        .execute();

        if (response.isEmpty()) {
            LOG.error("Got error when tried authorise user using Google OAuth: {}", response);
            addActionError(getText("user.cannotLoginWithGoogleAccount"));

            redirect = GruufActions.LOGIN;
        } else {
            LOG.debug("Got proper response from Google, trying to fetch user's data");
            GoogleIdToken idToken = response.parseIdToken();
            GoogleIdToken.Payload payload = idToken.getPayload();
            String emailAddress = payload.getEmail();
            String lastName = (String) payload.get("family_name");
            String firstName = (String) payload.get("given_name");

            LOG.debug("Got {} {} with emails {}", firstName, lastName, emailAddress);

            User user = registerAndLogin(Collections.singletonList(emailAddress), null, firstName, lastName);

            if (user != null) {
                markSessionAsLoggedIn(user);
                addActionMessage(getText("user.loggedInWithGoogleAccount", new String[]{user.getFullName(), user.getEmail()}));
                redirect = GruufActions.GARAGE;
            } else {
                LOG.debug("User is null, cannot login!");
                addActionError(getText("user.cannotLoginWithGoogleAccount"));
                redirect = GruufActions.LOGIN;
            }
        }

        return SUCCESS;
    }

    @Inject(GruufConstants.OAUTH_GOOGLE_API_KEY)
    public void setGoogleApiKey(String googleApiKey) {
        this.googleApiKey = googleApiKey;
    }

    @Inject(GruufConstants.OAUTH_GOOGLE_API_SECRET)
    public void setGoogleApiSecret(String googleApiSecret) {
        this.googleApiSecret = googleApiSecret;
    }

    @Inject(GruufConstants.HOST_URL)
    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirect() {
        return redirect;
    }

}
