package com.gruuf.web.actions;

import com.gruuf.services.Garage;
import com.opensymphony.xwork2.ActionSupport;
import com.gruuf.model.Bike;
import com.gruuf.model.User;
import com.gruuf.web.interceptors.GarageAware;
import com.gruuf.web.interceptors.CurrentUserAware;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Home extends ActionSupport implements CurrentUserAware, GarageAware {

    private static Logger LOG = LogManager.getLogger(Home.class);

    private User currentUser;
    private Garage garage;

    public String execute() throws Exception {
        return SUCCESS;
    }

    public List<Bike> getBikes() {
        List<Bike> bikes = garage.get(currentUser);

        LOG.debug("Found following bikes {} for user {}", bikes, currentUser);

        return bikes;
    }

    public void setUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }
}
