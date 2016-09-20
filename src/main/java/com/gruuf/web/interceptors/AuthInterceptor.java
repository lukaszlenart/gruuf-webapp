package com.gruuf.web.interceptors;

import com.gruuf.web.GruufActions;
import com.gruuf.web.RbwAuth;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.AnnotationUtils;
import com.gruuf.RbwServices;
import com.gruuf.auth.Tokens;
import com.gruuf.model.User;
import com.gruuf.services.UserStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthInterceptor extends AbstractInterceptor {

    private static Logger LOG = LogManager.getLogger(AuthInterceptor.class);

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        User currentUser = readCurrentUser(invocation);
        if (currentUser == null) {
            LOG.info("Current User is null, assuming not-logged-in");
            return invocation.invoke();
        } else {
            if (isAllowedUser(currentUser, invocation.getAction())) {
                LOG.debug("User {} is allowed to see {}", currentUser, invocation.getAction());
                return invocation.invoke();
            } else {
                LOG.debug("User {} is not allowed to see {}", currentUser, invocation.getAction());
                return GruufActions.GARAGE;
            }
        }
    }

    private User readCurrentUser(ActionInvocation invocation) {
        String authToken = (String) invocation.getInvocationContext().getSession().get(RbwAuth.AUTH_TOKEN);
        UserStore userStore = (UserStore) invocation.getInvocationContext().getApplication().get(RbwServices.USER_REGISTER);
        if (authToken == null) {
            LOG.debug("User not logged-in yet!");
            return null;
        }
        return userStore.get(authToken);
    }

    private boolean isAllowedUser(User user, Object secured) {
        Tokens tokens = AnnotationUtils.findAnnotation(secured.getClass(), Tokens.class);
        LOG.debug("Checking if user {} has required token [{}]", user, tokens);
        return tokens == null || user.hasAnyToken(tokens.value());
    }

}
