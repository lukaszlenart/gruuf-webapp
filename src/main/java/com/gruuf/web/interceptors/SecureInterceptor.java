package com.gruuf.web.interceptors;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsConstants;

public class SecureInterceptor extends AbstractInterceptor {

    private boolean devMode;

    @Inject(StrutsConstants.STRUTS_DEVMODE)
    public void setDevMode(String devMode) {
        this.devMode = Boolean.parseBoolean(devMode);
    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        if (devMode) {
            return invocation.invoke();
        } else {
            if (ServletActionContext.getRequest().isSecure()) {
                return invocation.invoke();
            } else {
                return "https-redirect";
            }
        }
    }
}
