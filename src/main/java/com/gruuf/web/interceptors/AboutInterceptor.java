package com.gruuf.web.interceptors;

import com.gruuf.GruufConstants;
import com.gruuf.web.GruufActions;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.Cookie;

public class AboutInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        if ("about".equalsIgnoreCase(invocation.getProxy().getActionName())) {
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

        return GruufActions.ABOUT;
    }

}
