package com.gruuf.services;

import com.gruuf.model.Bike;
import com.gruuf.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Garage extends Reindexable<Bike> {

    private static Logger LOG = LogManager.getLogger(Garage.class);

    public Garage(Class<Bike> type) {
        super(type);
    }

    @Override
    protected boolean shouldReindex() {
        // change to true when migrating data
        return false;
    }

    public List<Bike> findByOwner(User user) {
        LOG.debug("Fetching bikes for user {}", user);

        return findBy("owner =", user);
    }

    public boolean canView(Bike bike, User byUser) {
        LOG.debug("Checking if user {} can view bike {}", byUser, bike);
        return bike.getOwner().getId().equals(byUser.getId());
    }

    public Bike updateSpaceUsedBy(Bike bike, Long size) {
        Bike actual = get(bike.getId());
        Bike updated = Bike.clone(actual).addSpaceUsed(size).build();
        return put(updated);
    }
}
