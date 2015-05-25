package me.rbw.web.interceptors;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import me.rbw.RbwServices;
import me.rbw.services.Garage;

public class GarageInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        if (invocation.getAction() instanceof GarageAware) {
            Garage garage = (Garage) invocation.getInvocationContext().getApplication().get(RbwServices.GARAGE);
            ((GarageAware) invocation.getAction()).setGarage(garage);
        }
        return invocation.invoke();
    }

}
