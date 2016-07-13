package com.gruuf.web.actions.bike;

import com.gruuf.model.BikeDetails;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.User;
import com.gruuf.services.BikeHistory;
import com.gruuf.services.Garage;
import com.gruuf.web.RbwActions;
import com.gruuf.web.interceptors.BikeHistoryAware;
import com.gruuf.web.interceptors.CurrentUserAware;
import com.gruuf.web.interceptors.GarageAware;
import com.opensymphony.xwork2.ActionSupport;
import com.gruuf.model.Bike;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Show extends ActionSupport implements GarageAware, CurrentUserAware, BikeHistoryAware {

    private static Logger LOG = LogManager.getLogger(Show.class);

    private String bike;
    private BikeDetails bikeDetails;

    private Garage garage;
    private User currentUser;
    private BikeHistory bikeHistory;

    public String execute() {
        Bike selectedBike = garage.getBike(bike);
        if (garage.canView(selectedBike, currentUser)) {
            LOG.debug("User {} can view bike {}", selectedBike, currentUser);

            List<BikeEvent> events = bikeHistory.get(selectedBike.getId());
            bikeDetails = BikeDetails.create(selectedBike).withUser(currentUser).withHistory(events);

            return RbwActions.BIKE_SHOW;
        } else {
            LOG.warn("User {} cannot view bike {}", selectedBike, currentUser);
            return RbwActions.HOME;
        }
    }

    public void setBike(String bike) {
        this.bike = bike;
    }

    public BikeDetails getBikeDetails() {
        return bikeDetails;
    }

    @Override
    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    @Override
    public void setUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public void setBikeHistory(BikeHistory bikeHistory) {
        this.bikeHistory = bikeHistory;
    }
}
