package com.gruuf.web.actions;

import com.gruuf.model.Bike;
import com.gruuf.model.BikeDetails;
import com.gruuf.model.BikeEvent;
import com.gruuf.services.BikeHistory;
import com.gruuf.services.Garage;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.InterceptorRef;

import java.util.ArrayList;
import java.util.List;

@InterceptorRef("defaultWithMessages")
public class GarageAction extends BaseAction implements Preparable {

    private static Logger LOG = LogManager.getLogger(GarageAction.class);

    private Garage garage;
    private BikeHistory bikeHistory;

    private List<BikeDetails> bikesDetails;

    public String execute() throws Exception {
        return SUCCESS;
    }

    @Override
    public void prepare() throws Exception {
        bikesDetails = loadBikeDetails();
        LOG.debug("Found {} bikes assigned to the current user", bikesDetails.size());
    }

    public List<BikeDetails> getBikeDetails() {
        return bikesDetails;
    }

    public boolean isShowShortcuts() {
        return bikesDetails.size() > 2;
    }

    @Inject
    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    @Inject
    public void setBikeHistory(BikeHistory bikeHistory) {
        this.bikeHistory = bikeHistory;
    }

    private List<BikeDetails> loadBikeDetails() {
        List<BikeDetails> bikeDetails = new ArrayList<>();
        List<Bike> bikes = garage.findByOwner(currentUser);

        for (Bike bike : bikes) {
            List<BikeEvent> events = bikeHistory.listRecentByBike(bike);
            Long currentMileage = bikeHistory.findCurrentMileage(bike);
            Long currentMth = bikeHistory.findCurrentMth(bike);

            bikeDetails.add(
                    BikeDetails.create(bike)
                            .withUser(currentUser)
                            .withHistory(currentUser.getUserLocale(), events, currentMileage, currentMth)
            );
        }

        return bikeDetails;
    }

}
