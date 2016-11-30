package com.gruuf.web.actions;

import com.gruuf.model.Bike;
import com.gruuf.model.BikeDetails;
import com.gruuf.model.BikeEvent;
import com.gruuf.services.BikeHistory;
import com.gruuf.services.Garage;
import com.gruuf.web.actions.bike.HistoryAction;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.InterceptorRef;

import java.util.ArrayList;
import java.util.List;

@InterceptorRef("defaultWithMessages")
public class GarageAction extends BaseAction {

    private static Logger LOG = LogManager.getLogger(GarageAction.class);

    private Garage garage;
    private BikeHistory bikeHistory;

    public String execute() throws Exception {
        return SUCCESS;
    }

    public List<BikeDetails> getBikeDetails() {
        List<BikeDetails> bikes = loadBikeDetails();

        LOG.debug("Found following bikes {} for user {}", bikes, currentUser);

        return bikes;
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
            bikeDetails.add(
                    BikeDetails.create(bike)
                            .withUser(currentUser)
                            .withHistory(events)
                            .withMileage(bikeHistory.findCurrentMileage(bike))
            );
        }

        return bikeDetails;
    }

}
