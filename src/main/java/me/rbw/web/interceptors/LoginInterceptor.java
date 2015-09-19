package me.rbw.web.interceptors;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import me.rbw.web.RbwAuth;
import me.rbw.web.RbwActions;
import org.apache.struts2.StrutsConstants;

import java.util.HashSet;
import java.util.Set;

public class LoginInterceptor extends AbstractInterceptor {

    private Set<String> unsecureActions = new HashSet<String>() {
        {
            add(RbwActions.LOGIN);
            add(RbwActions.LOGIN_SUBMIT);
            add(RbwActions.USER_REGISTER);
            add(RbwActions.USER_REGISTER_SUBMIT);
        }
    };

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        if (isLoginAction(invocation.getProxy())) {
            return invocation.invoke();
        }
        if (isUserLoggedIn(invocation.getInvocationContext())) {
            return invocation.invoke();
        }
        return RbwActions.LOGIN;
    }

    private boolean isLoginAction(ActionProxy actionProxy) {
        String actionName = actionProxy.getActionName();
        return unsecureActions.contains(actionName);
    }

    private boolean isUserLoggedIn(ActionContext context) {
        return context.getSession().get(RbwAuth.AUTH_TOKEN) != null;
    }

}
