package com.gruuf.web.interceptors;

import com.gruuf.web.RbwAuth;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.AnnotationUtils;
import com.gruuf.auth.Anonymous;
import com.gruuf.web.RbwActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginInterceptor extends AbstractInterceptor {

    private static Logger LOG = LogManager.getLogger(LoginInterceptor.class);

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        if (isAllowedAction(invocation.getAction())) {
            LOG.debug("Action {} is allowed to access without logging in", invocation.getProxy().getActionName());
            return invocation.invoke();
        }
        if (isUserLoggedIn(invocation.getInvocationContext())) {
            LOG.debug("User is logged in, allowing to access action {}", invocation.getProxy().getActionName());
            return invocation.invoke();
        }
        return RbwActions.LOGIN;
    }

    private boolean isAllowedAction(Object action) {
        LOG.debug("Checking if action is marked with {} to get access", Anonymous.class.getName());
        return AnnotationUtils.findAnnotation(action.getClass(), Anonymous.class) != null;
    }

    private boolean isUserLoggedIn(ActionContext context) {
        LOG.debug("Checking if token {} exists", RbwAuth.AUTH_TOKEN);
        return context.getSession().get(RbwAuth.AUTH_TOKEN) != null;
    }

}
