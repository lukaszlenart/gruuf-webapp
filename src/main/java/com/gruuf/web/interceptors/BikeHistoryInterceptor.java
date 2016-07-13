package com.gruuf.web.interceptors;

import com.gruuf.services.BikeHistory;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.gruuf.RbwServices;

public class BikeHistoryInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        if (invocation.getAction() instanceof BikeHistoryAware) {
            BikeHistory eventSource = (BikeHistory) invocation.getInvocationContext().getApplication().get(RbwServices.BIKE_HISTORY);
            ((BikeHistoryAware) invocation.getAction()).setBikeHistory(eventSource);
        }
        return invocation.invoke();
    }

}
