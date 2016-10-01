package com.gruuf.web.interceptors;

import com.gruuf.auth.BikeRestriction;
import com.gruuf.model.Bike;
import com.gruuf.model.User;
import com.gruuf.services.Garage;
import com.gruuf.services.UserStore;
import com.gruuf.web.GruufAuth;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.AnnotationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.Parameter;

public class BikeRestrictionInterceptor extends AbstractInterceptor {

    private static final Logger LOG = LogManager.getLogger(BikeRestrictionInterceptor.class);

    private UserStore userStore;
    private Garage garage;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        Object action = invocation.getAction();

        BikeRestriction bikeRestriction = AnnotationUtils.findAnnotation(action.getClass(), BikeRestriction.class);
        if (bikeRestriction != null) {
            LOG.debug("Bike restriction set {}", bikeRestriction);

            String paramName = bikeRestriction.value();
            Parameter parameter = invocation.getInvocationContext().getParameters().get(paramName);
            LOG.debug("Bike ID value: {}", parameter.getValue());

            String authToken = (String) invocation.getInvocationContext().getSession().get(GruufAuth.AUTH_TOKEN);
            User currentUser = userStore.get(authToken);

            if (parameter.isDefined()) {
                Bike bike = garage.get(parameter.getValue());
                if (garage.canView(bike, currentUser)) {
                    if (action instanceof BikeAware) {
                        ((BikeAware) action).setBike(bike);
                    }
                    return invocation.invoke();
                } else {
                    LOG.warn("User {} cannot view bike {}", currentUser, bike);
                }
            } else if (bikeRestriction.ignoreNullParam()) {
                LOG.info("Ignoring null param {} and continue with action {}", bikeRestriction.value(), action);
                return invocation.invoke();
            } else {
                LOG.warn("Parameter {} not defined", bikeRestriction.value());
            }

            return bikeRestriction.resultName();
        }

        return invocation.invoke();
    }

    @Inject
    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    @Inject
    public void setGarage(Garage garage) {
        this.garage = garage;
    }

}
