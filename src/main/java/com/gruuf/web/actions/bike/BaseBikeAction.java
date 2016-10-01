package com.gruuf.web.actions.bike;

import com.gruuf.model.Bike;
import com.gruuf.model.BikeDetails;
import com.gruuf.model.BikeEvent;
import com.gruuf.services.BikeHistory;
import com.gruuf.services.Garage;
import com.gruuf.web.actions.BaseAction;
import com.gruuf.web.interceptors.BikeAware;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

@Result(name = BaseBikeAction.TO_SHOW_BIKE, location = "show", type = "redirectAction", params = {"bikeId", "${bikeId}"})
public abstract class BaseBikeAction extends BaseAction implements BikeAware {

    public static final String TO_SHOW_BIKE = "to-show-bike";

    protected Bike selectedBike;

    protected Garage garage;
    protected BikeHistory bikeHistory;

    @Inject
    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    @Inject
    public void setBikeHistory(BikeHistory bikeHistory) {
        this.bikeHistory = bikeHistory;
    }

    public String getBikeName() {
        if (selectedBike != null) {
            return selectedBike.getName();
        }
        return "";
    }

    protected BikeDetails loadBikeDetails() {
        List<BikeEvent> events = bikeHistory.listByBike(selectedBike);
        LOG.debug("Found Bike Events for bike {}: {}", selectedBike, events);

        return BikeDetails.create(selectedBike).withUser(currentUser).withHistory(events);
    }

    @Override
    public void setBike(Bike bike) {
        this.selectedBike = bike;
    }

    public String getBikeId() {
        return selectedBike.getId();
    }

    public String getBikeFormTitle() {
        if (selectedBike == null) {
            return getText("bike.addNewBike");
        } else {
            return getText("bike.editBike");
        }
    }
}
