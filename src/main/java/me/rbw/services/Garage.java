package me.rbw.services;

import com.googlecode.objectify.ObjectifyService;
import me.rbw.model.Bike;
import me.rbw.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Garage {

    private static Logger LOG = LogManager.getLogger(Garage.class);

    public List<Bike> get(User user) {
        LOG.debug("Fetching bikes for user {}", user);

        return ObjectifyService
                .ofy()
                .load()
                .type(Bike.class)
                .filter("owner =", user)
                .list();
    }

    public void put(Bike bike) {
        LOG.debug("Storing bike {}", bike);

        ObjectifyService
                .ofy()
                .save()
                .entity(bike)
                .now();
    }

    public Bike getBike(String bikeId) {
        LOG.debug("Fetching bike's details for {}", bikeId);

        return ObjectifyService
                .ofy()
                .load()
                .type(Bike.class)
                .id(bikeId)
                .now();
    }

    public boolean canView(Bike bike, User byUser) {
        LOG.debug("Checking if user {} can view bike {}", byUser, bike);
        return bike.getOwner().getId().equals(byUser.getId());
    }

}
