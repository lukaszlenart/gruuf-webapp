package com.gruuf.services;

import com.gruuf.model.User;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.apache.struts2.StrutsConstants;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class MailBox {

    private static final Logger LOG = LogManager.getLogger(MailBox.class);

    public static final String NOREPLY_GRUUF_COM = "no-reply@gruuf.com";
    public static final String GRUUF_APP = "Gruuf App";

    private UserStore userStore;
    private boolean devMode;

    public void notifyAdmin(String subject, String text, Object... bodyParams) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            Message msg = new MimeMessage(session);
            addAdmins(msg);
            msg.setFrom(new InternetAddress(NOREPLY_GRUUF_COM, GRUUF_APP));
            msg.setSubject("[Gruuf] " + subject);

            String body = text;
            for (Object param : bodyParams) {
                body = body + "\n" + String.valueOf(param);
            }
            msg.setText(body);

            if (devMode) {
                LOG.info("\nSending mail:\n{}\n", buildMessageInfo(msg));
            } else {
                Transport.send(msg);
            }
        } catch (Exception e) {
            LOG.error(new ParameterizedMessage("Cannot notify admin about '{}'", subject), e);
        }
    }

    private String buildMessageInfo(Message msg) throws MessagingException, IOException {
        return "Subject: " + msg.getSubject() +
                "\nBody: " + msg.getContent() +
                "\nTo: " + Arrays.toString(msg.getAllRecipients());
    }

    private void addAdmins(Message msg) {
        List<User> admins = userStore.listAdmins();
        for (User admin : admins) {
            try {
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(admin.getEmail(), admin.getFullName()));
            } catch (Exception e) {
                LOG.error(new ParameterizedMessage("Cannot create e-mail address for {}", admin), e);
            }
        }
    }

    @Inject
    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    @Inject(StrutsConstants.STRUTS_DEVMODE)
    public void setDevMode(String devMode) {
        this.devMode = Boolean.parseBoolean(devMode);
    }
}
