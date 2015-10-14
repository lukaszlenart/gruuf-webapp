package me.rbw.services;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import me.rbw.model.Bike;
import me.rbw.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Garage {

    private static Logger LOG = LogManager.getLogger(Garage.class);

    public List<Bike> get(String userId) {
        Key<User> user = Key.create(User.class, userId);

        LOG.debug("Fetching bikes for user {}", userId);

        return ObjectifyService
                .ofy()
                .load()
                .type(Bike.class)
                .ancestor(user)
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
}
