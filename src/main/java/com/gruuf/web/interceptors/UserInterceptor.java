package com.gruuf.web.interceptors;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.gruuf.RbwServices;
import com.gruuf.model.User;
import com.gruuf.services.UserStore;
import com.gruuf.web.RbwAuth;

public class UserInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        if (invocation.getAction() instanceof CurrentUserAware) {
            String authToken = (String) invocation.getInvocationContext().getSession().get(RbwAuth.AUTH_TOKEN);
            UserStore userStore = (UserStore) invocation.getInvocationContext().getApplication().get(RbwServices.USER_REGISTER);
            User currentUser = userStore.get(authToken);
            ((CurrentUserAware) invocation.getAction()).setUser(currentUser);
        }

        if (invocation.getAction() instanceof UserStoreAware) {
            UserStoreAware userStoreAware = (UserStoreAware) invocation.getAction();
            UserStore userStore = (UserStore) invocation.getInvocationContext().getApplication().get(RbwServices.USER_REGISTER);
            userStoreAware.setUserStore(userStore);
        }

        return invocation.invoke();
    }

}
