package me.rbw.web.interceptors;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import me.rbw.RbwServices;
import me.rbw.services.BikeHistory;

public class BikeHistoryInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        if (invocation.getAction() instanceof BikeHistoryAware) {
            BikeHistory eventSource = (BikeHistory) invocation.getInvocationContext().getApplication().get(RbwServices.MOTORBIKE_EVENT_SOURCE);
            ((BikeHistoryAware) invocation.getAction()).setMotorbikeEventSource(eventSource);
        }
        return invocation.invoke();
    }

}
