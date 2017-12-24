package com.gruuf.web.actions;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.send.MessagePayload;
import com.github.messenger4j.send.Payload;
import com.github.messenger4j.send.message.TextMessage;
import com.github.messenger4j.webhook.event.TextMessageEvent;
import com.gruuf.GruufConstants;
import com.gruuf.auth.Anonymous;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.opensymphony.xwork2.Action.ERROR;

@Anonymous
@Results({
        @Result(name = ERROR, type = "httpheader", params = {"status", "403"})
})
public class BotAction extends BaseAction implements ServletRequestAware {

    private static final Logger LOG = LogManager.getLogger(BotAction.class);

    private String pageAccessToken;
    private String appSecret;
    private String verifyToken;

    private HttpServletRequest request;

    public String execute() throws Exception {
        String body = request.getReader().lines().collect(Collectors.joining());
        final Messenger messenger = Messenger.create(pageAccessToken, appSecret, verifyToken);

        messenger.onReceiveEvents(body, Optional.empty(), event -> {
            final String senderId = event.senderId();

            Payload response;
            if (event.isTextMessageEvent()) {
                TextMessageEvent message = event.asTextMessageEvent();
                LOG.debug("Got test message event: {}", event);
                response = MessagePayload.create(senderId, TextMessage.create("Hello! I'm a bot but still not full functional, sorry for that :("));
            } else {
                response = MessagePayload.create(senderId, TextMessage.create("I only support raw text messages for now, sorry for that :("));
            }
            try {
                messenger.send(response);
            } catch (MessengerApiException | MessengerIOException e) {
                LOG.error(new ParameterizedMessage("Cannot response user {}: {}", senderId, event), e);
            }
        });

        return NONE;
    }

    /*
    public String verify() throws Exception {
        final String mode = request.getParameter(Messenger.MODE_REQUEST_PARAM_NAME);
        final String challenge = request.getParameter(Messenger.CHALLENGE_REQUEST_PARAM_NAME);
        final String token = request.getParameter(Messenger.VERIFY_TOKEN_REQUEST_PARAM_NAME);

        final Messenger messenger = Messenger.create(pageAccessToken, appSecret, verifyToken);
        try {
            messenger.verifyWebhook(mode, token);

            PrintWriter out = ServletActionContext.getResponse().getWriter();
            out.write(challenge);

            return NONE;
        } catch (MessengerVerificationException e) {
            return ERROR;
        }
    }
     */
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Inject(GruufConstants.FACEBOOK_PAGE_ACCESS_TOKEN)
    public void setPageAccessToken(String pageAccessToken) {
        this.pageAccessToken = pageAccessToken;
    }

    @Inject(GruufConstants.FACEBOOK_APP_SECRET)
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    @Inject(GruufConstants.FACEBOOK_VERIFY_TOKEN)
    public void setVerifyToken(String verifyToken) {
        this.verifyToken = verifyToken;
    }
}
