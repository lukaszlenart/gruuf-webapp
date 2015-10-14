package me.rbw.web;

import com.googlecode.objectify.ObjectifyService;
import me.rbw.RbwServices;
import me.rbw.model.EventType;
import me.rbw.model.Bike;
import me.rbw.model.BikeEvent;
import me.rbw.model.User;
import me.rbw.services.Garage;
import me.rbw.services.BikeHistory;
import me.rbw.services.UserStore;
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
        final UserStore userStore = new UserStore();
        final BikeHistory eventSource = new BikeHistory();

        sce.getServletContext().setAttribute(RbwServices.GARAGE, garage);
        sce.getServletContext().setAttribute(RbwServices.USER_REGISTER, userStore);
        sce.getServletContext().setAttribute(RbwServices.MOTORBIKE_EVENT_SOURCE, eventSource);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute(RbwServices.GARAGE);
        sce.getServletContext().removeAttribute(RbwServices.USER_REGISTER);
        sce.getServletContext().removeAttribute(RbwServices.MOTORBIKE_EVENT_SOURCE);
    }

}
