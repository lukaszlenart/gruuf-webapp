package com.gruuf.web.actions;

import com.gruuf.model.Bike;
import com.gruuf.web.interceptors.GarageAware;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GarageAction extends BaseAction implements GarageAware {

    private static Logger LOG = LogManager.getLogger(GarageAction.class);

    private com.gruuf.services.Garage garage;

    public String execute() throws Exception {
        return SUCCESS;
    }

    public List<Bike> getBikes() {
        List<Bike> bikes = garage.get(currentUser);

        LOG.debug("Found following bikes {} for user {}", bikes, currentUser);

        return bikes;
    }

    public void setGarage(com.gruuf.services.Garage garage) {
        this.garage = garage;
    }
}
