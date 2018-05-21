package com.gruuf.web.interceptors;

import com.gruuf.GruufConstants;
import com.gruuf.web.GlobalResult;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.Cookie;

public class AboutInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        if (isIgnoredAction(invocation) || isIgnoredNamespace(invocation)) {
            return invocation.invoke();
        }

        Cookie[] cookies = ServletActionContext.getRequest().getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (GruufConstants.FIRST_TIME_COOKIE.equalsIgnoreCase(cookie.getName())) {
                    return invocation.invoke();
                }
            }
        }

        return GlobalResult.ABOUT;
    }

    private boolean isIgnoredNamespace(ActionInvocation invocation) {
        String namespace = invocation.getProxy().getNamespace();

        return namespace.startsWith("/api")
            || namespace.startsWith("/cron")
            || namespace.startsWith("/tasks");
    }

    private boolean isIgnoredAction(ActionInvocation invocation) {
        String actionName = invocation.getProxy().getActionName();
        String namespace = invocation.getProxy().getNamespace();

        return "about".equalsIgnoreCase(actionName)
            || "privacy-policy".equalsIgnoreCase(actionName)
            || ("accept".equalsIgnoreCase(actionName) && namespace.startsWith("/transfer"));
    }

}
