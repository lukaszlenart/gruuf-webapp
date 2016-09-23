package com.gruuf.web;

import com.googlecode.objectify.ObjectifyService;
import com.gruuf.model.Bike;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.EventType;
import com.gruuf.model.User;
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
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }

}
