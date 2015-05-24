package me.rbw.web.interceptors;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import me.rbw.web.RbwAuth;
import me.rbw.web.RbwResults;
import org.apache.struts2.StrutsConstants;

public class LoginInterceptor extends AbstractInterceptor {

    private boolean devMode = false;

    @Inject(StrutsConstants.STRUTS_DEVMODE)
    public void setDevMode(String devMode) {
        this.devMode = Boolean.parseBoolean(devMode);
    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        if (isLoginAction(invocation.getProxy())) {
            return invocation.invoke();
        }
        if (isUserLoggedIn(invocation.getInvocationContext())) {
            return invocation.invoke();
        }
        return RbwResults.LOGIN;
    }

    private boolean isLoginAction(ActionProxy actionProxy) {
        return actionProxy.getActionName().equals(RbwResults.LOGIN) || actionProxy.getActionName().equals(RbwResults.LOGIN_SUBMIT);
    }

    private boolean isUserLoggedIn(ActionContext context) {
        return devMode || context.getSession().get(RbwAuth.AUTH_TOKEN) != null;
    }

}
