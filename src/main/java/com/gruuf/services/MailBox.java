package com.gruuf.services;

import com.gruuf.model.User;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.apache.struts2.StrutsConstants;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class MailBox {

    private static final Logger LOG = LogManager.getLogger(MailBox.class);

    public static final String NOREPLY_GRUUF_COM = "noreply@gruuf-webapp.appspotmail.com";
    //public static final String NOREPLY_GRUUF_COM = "noreply@gruuf.com";
    public static final String GRUUF_APP = "Gruuf App";

    private UserStore userStore;
    private boolean devMode;

    public void notifyAdmin(String subject, String text, Object... bodyParams) {
        try {
            Message msg = prepareMessage(subject);
            addAdmins(msg);
            sendMessage(text, msg, bodyParams);
        } catch (Exception e) {
            LOG.error(new ParameterizedMessage("Cannot notify admin about '{}'", subject), e);
        }
    }

    public void notifyOwner(String ownerEmail, String fullName, String subject, String text, Object... bodyParams) {
        try {
            Message msg = prepareMessage(subject);
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(ownerEmail, fullName));
            sendMessage(text, msg, bodyParams);
        } catch (Exception e) {
            LOG.error(new ParameterizedMessage("Cannot notify owner {} about '{}'", fullName, subject), e);
        }
    }

    private Message prepareMessage(String subject) throws MessagingException, UnsupportedEncodingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(NOREPLY_GRUUF_COM, GRUUF_APP));
        msg.setSubject("[Gruuf] " + subject);
        return msg;
    }

    private void sendMessage(String text, Message msg, Object[] bodyParams) throws MessagingException, IOException {
        StringBuilder body = new StringBuilder(text);
        for (Object param : bodyParams) {
            body.append("\n").append(String.valueOf(param));
        }
        msg.setText(body.toString());

        if (devMode) {
            LOG.info("\nSending mail:\n{}\n", buildMessageInfo(msg));
        } else {
            Transport.send(msg);
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
