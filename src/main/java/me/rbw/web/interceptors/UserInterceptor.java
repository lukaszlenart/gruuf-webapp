package me.rbw.web.interceptors;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import me.rbw.RbwServices;
import me.rbw.model.User;
import me.rbw.services.UserStore;
import me.rbw.web.RbwAuth;

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
