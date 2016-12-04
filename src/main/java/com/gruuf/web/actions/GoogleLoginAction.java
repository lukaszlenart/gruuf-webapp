package com.gruuf.web.actions;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.gruuf.GruufConstants;
import com.gruuf.auth.Anonymous;
import com.gruuf.model.User;
import com.gruuf.web.GruufActions;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.json.JSONReader;
import org.apache.struts2.result.ServletRedirectResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Anonymous
@InterceptorRef("defaultWithMessages")
public class GoogleLoginAction extends BaseLoginAction implements Preparable {

    public static final String PROFILE_URL = "https://www.googleapis.com/plus/v1/people/me";

    private String googleApiKey;
    private String googleApiSecret;
    private String hostUrl;
    private OAuth20Service service;

    private String code;

    @Action("google-login")
    public Object googleLogin() {
        final Map<String, String> additionalParams = new HashMap<>();
        additionalParams.put("access_type", "offline");
        additionalParams.put("prompt", "consent");

        return new ServletRedirectResult(service.getAuthorizationUrl(additionalParams));
    }

    @Action("google-confirm")
    public String googleConfirm() throws Exception {

        OAuth2AccessToken accessToken = service.getAccessToken(code);
        accessToken = service.refreshAccessToken(accessToken.getRefreshToken());

        OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_URL + "?fields=emails,name(familyName,givenName)", service);
        service.signRequest(accessToken, request);

        final Response response = request.send();

        if (response.getCode() == 200) {
            LOG.debug("Got proper response from Google, trying to fetch user's data");
            Object userData = new JSONReader().read(response.getBody());
            List<String> emailAddress = extractEmail(userData);
            String firstName = extractFirstName(userData);
            String lastName = extractLastName(userData);

            LOG.debug("Got {} {} with emails {}", firstName, lastName, emailAddress);

            User user = registerAndLogin(emailAddress, null, firstName, lastName);

            if (user != null) {
                markSessionAsLoggedIn(user);
                addActionMessage(getText("user.loggedInWithGoogleAccount", new String[] { user.getFullName(), user.getEmail() }));
                return GruufActions.GARAGE;
            } else {
                LOG.debug("User is null, cannot login!");
                addActionError(getText("user.cannotLoginWithGoogleAccount"));
                return GruufActions.LOGIN;
            }
        } else {
            LOG.error("Got error [{}] when tried authorise user using Google OAuth: {}", response.getCode(), response.getBody());
            addActionError(getText("user.cannotLoginWithGoogleAccount"));

            return GruufActions.LOGIN;
        }
    }

    private String extractLastName(Object userData) {
        Map data = (Map) userData;
        Map nameMap = (Map) data.get("name");
        return String.valueOf(nameMap.get("familyName"));
    }

    private String extractFirstName(Object userData) {
        Map data = (Map) userData;
        Map nameMap = (Map) data.get("name");
        return String.valueOf(nameMap.get("givenName"));
    }

    private List<String> extractEmail(Object userData) {
        List<String> emails = new ArrayList<>();

        Map data = (Map) userData;
        List emailList = (List) data.get("emails");
        for (Object emailMap : emailList) {
            String email = String.valueOf(((Map)emailMap).get("value"));
            emails.add(email);
        }

        return emails;
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

    @Override
    public void prepare() throws Exception {
        service = new ServiceBuilder()
                .apiKey(googleApiKey)
                .apiSecret(googleApiSecret)
                .callback(hostUrl + "/google-confirm")
                .scope("email profile")
                .build(GoogleApi20.instance());
    }

    public void setCode(String code) {
        this.code = code;
    }

}
