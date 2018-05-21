package com.gruuf.web.interceptors;

import com.gruuf.auth.Bot;
import com.gruuf.model.User;
import com.gruuf.services.UserStore;
import com.gruuf.web.GruufAuth;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.AnnotationUtils;
import com.gruuf.auth.Anonymous;
import com.gruuf.web.GlobalResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginInterceptor extends AbstractInterceptor {

    private static Logger LOG = LogManager.getLogger(LoginInterceptor.class);

    @Inject
    private UserStore userStore;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        if (isAllowedAction(invocation.getAction())) {
            LOG.debug("Action {} is allowed to access without logging in", invocation.getProxy().getActionName());
            return invocation.invoke();
        }
        if (isBotAction(invocation.getAction())) {
            LOG.debug("Action {} is allowed to access via a Bot", invocation.getProxy().getActionName());
            String asid = invocation.getInvocationContext().getParameters().get("asid").getValue();
            if (asid != null) {
                User user = userStore.findUniqueBy("facebookId", asid);
                if (user != null) {
                    invocation.getInvocationContext().setLocale(user.getUserLocale().toLocale());
                }
            }
            return invocation.invoke();
        }
        if (isUserLoggedIn(invocation.getInvocationContext())) {
            LOG.debug("User is logged in, allowing to access action {}", invocation.getProxy().getActionName());
            return invocation.invoke();
        }
        return GlobalResult.LOGIN;
    }

    private boolean isAllowedAction(Object action) {
        LOG.debug("Checking if action is marked with {} to get access", Anonymous.class.getName());
        return AnnotationUtils.findAnnotation(action.getClass(), Anonymous.class) != null;
    }

    private boolean isBotAction(Object action) {
        LOG.debug("Checking if action is marked with {} to get access", Bot.class.getName());
        return AnnotationUtils.findAnnotation(action.getClass(), Bot.class) != null;
    }

    private boolean isUserLoggedIn(ActionContext context) {
        LOG.debug("Checking if token {} exists", GruufAuth.AUTH_TOKEN);
        return context.getSession().get(GruufAuth.AUTH_TOKEN) != null;
    }

}
