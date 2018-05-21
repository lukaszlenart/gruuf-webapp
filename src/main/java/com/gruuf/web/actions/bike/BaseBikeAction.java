package com.gruuf.web.actions.bike;

import com.gruuf.model.Bike;
import com.gruuf.model.BikeDescriptor;
import com.gruuf.model.BikeDetails;
import com.gruuf.model.BikeEvent;
import com.gruuf.services.BikeHistory;
import com.gruuf.services.EventTypes;
import com.gruuf.services.Garage;
import com.gruuf.web.actions.BaseAction;
import com.gruuf.web.interceptors.BikeAware;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

@Result(name = BaseBikeAction.TO_SHOW_BIKE, location = "history", type = "redirectAction", params = {"bikeId", "${bikeId}"})
public abstract class BaseBikeAction extends BaseAction implements BikeAware {

    public static final String TO_SHOW_BIKE = "to-show-bike";

    private static final Logger LOG = LogManager.getLogger(BaseBikeAction.class);

    protected Bike selectedBike;

    @Inject
    protected Garage garage;
    @Inject
    protected BikeHistory bikeHistory;
    @Inject
    protected EventTypes eventTypes;

    public String getBikeName() {
        if (selectedBike != null) {
            return selectedBike.getName();
        }
        return "";
    }

    protected BikeDetails loadBikeDetails() {
        List<BikeEvent> events = bikeHistory.listByBike(selectedBike);
        LOG.debug("Found Bike Events for bike {}: {}", selectedBike, events);

        Long currentMileage = bikeHistory.findCurrentMileage(selectedBike);
        Long currentMth = bikeHistory.findCurrentMth(selectedBike);

        return BikeDetails.create(selectedBike)
            .withUser(currentUser)
            .withHistory(currentUser.getUserLocale(), events, currentMileage, currentMth);
    }

    @Override
    public void setBike(Bike bike) {
        this.selectedBike = bike;
    }

    public BikeDescriptor getSelectedBike() {
        return new BikeDescriptor(selectedBike);
    }

    public String getBikeId() {
        if (selectedBike != null) {
            return selectedBike.getId();
        }
        return null;
    }

    public String getBikeFormTitle() {
        if (selectedBike == null) {
            return getText("bike.addNewBike");
        } else {
            return getText("bike.editBike");
        }
    }
}
