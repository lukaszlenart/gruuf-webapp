package me.rbw.web.interceptors;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import me.rbw.RbwServices;
import me.rbw.model.User;
import me.rbw.services.UserRegister;
import me.rbw.web.RbwAuth;

public class UserInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        if (invocation.getAction() instanceof UserAware) {
            String authToken = (String) invocation.getInvocationContext().getSession().get(RbwAuth.AUTH_TOKEN);
            UserRegister userRegister = (UserRegister) invocation.getInvocationContext().getApplication().get(RbwServices.USER_REGISTER);
            User currentUser = userRegister.get(authToken);
            ((UserAware) invocation.getAction()).setUser(currentUser);
        }

        if (invocation.getAction() instanceof RegisterUserAware) {
            RegisterUserAware registerUserAware = (RegisterUserAware) invocation.getAction();

            User newUser = registerUserAware.registerUser();
            UserRegister userRegister = (UserRegister) invocation.getInvocationContext().getApplication().get(RbwServices.USER_REGISTER);
            userRegister.put(newUser);
        }

        if (invocation.getAction() instanceof UserStoreAware) {
            UserStoreAware userStoreAware = (UserStoreAware) invocation.getAction();
            UserRegister userRegister = (UserRegister) invocation.getInvocationContext().getApplication().get(RbwServices.USER_REGISTER);
            userStoreAware.setUserService(userRegister);
        }

        return invocation.invoke();
    }

}
