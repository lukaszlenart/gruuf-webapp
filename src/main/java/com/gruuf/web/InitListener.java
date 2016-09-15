package com.gruuf.web;

import com.googlecode.objectify.ObjectifyService;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.EventType;
import com.gruuf.model.User;
import com.gruuf.services.BikeHistory;
import com.gruuf.services.Garage;
import com.gruuf.RbwServices;
import com.gruuf.model.Bike;
import com.gruuf.services.Reindexable;
import com.gruuf.services.UserStore;
import ognl.OgnlRuntime;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        OgnlRuntime.setSecurityManager(null);

        ObjectifyService.register(User.class);
        ObjectifyService.register(EventType.class);
        ObjectifyService.register(BikeEvent.class);
        ObjectifyService.register(Bike.class);

        final Garage garage = new Garage();
        garage.reindex();

        final UserStore userStore = new UserStore();
        final BikeHistory bikeHistory = new BikeHistory();

        sce.getServletContext().setAttribute(RbwServices.GARAGE, garage);
        sce.getServletContext().setAttribute(RbwServices.USER_REGISTER, userStore);
        sce.getServletContext().setAttribute(RbwServices.BIKE_HISTORY, bikeHistory);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute(RbwServices.GARAGE);
        sce.getServletContext().removeAttribute(RbwServices.USER_REGISTER);
        sce.getServletContext().removeAttribute(RbwServices.BIKE_HISTORY);
    }

}
