package com.gruuf.web.interceptors;

import com.gruuf.model.User;
import com.gruuf.services.UserStore;
import com.gruuf.web.GruufAuth;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserInterceptor extends AbstractInterceptor {

    private static Logger LOG = LogManager.getLogger(UserInterceptor.class);

    private UserStore userStore;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        if (invocation.getAction() instanceof CurrentUserAware) {
            LOG.debug("Action implements {}, injecting user", CurrentUserAware.class.getSimpleName());

            String authToken = (String) invocation.getInvocationContext().getSession().get(GruufAuth.AUTH_TOKEN);
            if (authToken == null) {
                LOG.debug("AuthToken is null, assuming not-logged-in");
                ((CurrentUserAware) invocation.getAction()).setUser(null);
            } else {
                LOG.debug("AuthToken is {}, fetching user from store", authToken);
                User currentUser = userStore.get(authToken);
                ((CurrentUserAware) invocation.getAction()).setUser(currentUser);
            }
        }

        return invocation.invoke();
    }

    @Inject
    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }
}
