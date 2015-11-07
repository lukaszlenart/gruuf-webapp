package me.rbw.auth;

import com.opensymphony.xwork2.util.AnnotationUtils;
import me.rbw.model.User;

public class AuthChecker {

    public static boolean isAllowedUser(User user, Object secured) {
        Token token = AnnotationUtils.findAnnotation(secured.getClass(), Token.class);
        return token == null || user.getTokens().contains(token.value());
    }

}
