package me.rbw.web.actions.bike;

import com.opensymphony.xwork2.ActionSupport;
import me.rbw.model.Bike;
import me.rbw.model.BikeDetails;
import me.rbw.model.BikeEvent;
import me.rbw.model.User;
import me.rbw.services.BikeHistory;
import me.rbw.services.Garage;
import me.rbw.web.RbwActions;
import me.rbw.web.interceptors.BikeHistoryAware;
import me.rbw.web.interceptors.CurrentUserAware;
import me.rbw.web.interceptors.GarageAware;
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
