package com.gruuf.web.interceptors;

import com.gruuf.model.User;
import com.gruuf.services.UserStore;
import com.gruuf.web.GruufAuth;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PolicyInterceptor extends AbstractInterceptor {

    private static Logger LOG = LogManager.getLogger(PolicyInterceptor.class);

    @Inject
    private UserStore userStore;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        if ("privacy-policy".equalsIgnoreCase(invocation.getProxy().getActionName())) {
            return invocation.invoke();
        }

        if ("approve-policy".equalsIgnoreCase(invocation.getProxy().getActionName())) {
            return invocation.invoke();
        }

        User currentUser = readCurrentUser(invocation);

        if (currentUser != null && !currentUser.isPrivacyPolicyAccepted()) {
            LOG.warn("Current user did not accept the Privacy Policy yet!");
            return BaseAction.PRIVACY_POLICY;
        }

        return invocation.invoke();
    }

    private User readCurrentUser(ActionInvocation invocation) {
        String authToken = (String) invocation.getInvocationContext().getSession().get(GruufAuth.AUTH_TOKEN);
        if (authToken == null) {
            LOG.debug("User not logged-in yet!");
            return null;
        }
        return userStore.get(authToken);
    }

}
