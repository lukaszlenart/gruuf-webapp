package me.rbw.web;

import com.googlecode.objectify.ObjectifyService;
import me.rbw.RbwServices;
import me.rbw.model.Motorbike;
import me.rbw.model.User;
import me.rbw.services.Garage;
import me.rbw.services.UserStore;
import ognl.OgnlRuntime;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        OgnlRuntime.setSecurityManager(null);

        ObjectifyService.register(User.class);
        ObjectifyService.register(Motorbike.class);

        final Garage garage = new Garage();
        final UserStore userStore = new UserStore();

        sce.getServletContext().setAttribute(RbwServices.GARAGE, garage);
        sce.getServletContext().setAttribute(RbwServices.USER_REGISTER, userStore);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute(RbwServices.GARAGE);
        sce.getServletContext().removeAttribute(RbwServices.USER_REGISTER);
    }

}
