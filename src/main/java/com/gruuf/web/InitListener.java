package com.gruuf.web;

import com.googlecode.objectify.ObjectifyService;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.EventType;
import com.gruuf.model.User;
import com.gruuf.services.BikeHistory;
import com.gruuf.services.Garage;
import com.gruuf.services.GruufServices;
import com.gruuf.model.Bike;
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

        final BikeHistory bikeHistory = new BikeHistory();
        bikeHistory.reindex();

        sce.getServletContext().setAttribute(GruufServices.GARAGE, garage);
        sce.getServletContext().setAttribute(GruufServices.BIKE_HISTORY, bikeHistory);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute(GruufServices.GARAGE);
        sce.getServletContext().removeAttribute(GruufServices.BIKE_HISTORY);
    }

}
