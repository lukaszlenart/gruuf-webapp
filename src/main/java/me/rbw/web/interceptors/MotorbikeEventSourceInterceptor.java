package me.rbw.web.interceptors;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import me.rbw.RbwServices;
import me.rbw.services.MotorbikeEventSource;

public class MotorbikeEventSourceInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        if (invocation.getAction() instanceof MotorbikeEventsAware) {
            MotorbikeEventSource eventSource = (MotorbikeEventSource) invocation.getInvocationContext().getApplication().get(RbwServices.MOTORBIKE_EVENT_SOURCE);
            ((MotorbikeEventsAware) invocation.getAction()).setMotorbikeEventSource(eventSource);
        }
        return invocation.invoke();
    }

}
