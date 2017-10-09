package com.gruuf.services;

import com.gruuf.model.Bike;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.BikeEventStatus;

import java.util.Arrays;
import java.util.List;

public class BikeHistory extends Reindexable<BikeEvent> {

    public BikeHistory(Class<BikeEvent> type) {
        super(type);
    }

    @Override
    protected boolean shouldReindex() {
        // change to true when migrating data
        return true;
    }

    public List<BikeEvent> listByBike(Bike bike) {
        return filter("bike =", bike)
                .filter("status in", Arrays.asList(BikeEventStatus.NEW, BikeEventStatus.SYSTEM))
                .order("-registerDate")
                .list();
    }

    public List<BikeEvent> listRecentByBike(Bike bike) {
        return filter("bike =", bike)
                .limit(4)
                .filter("status =", BikeEventStatus.NEW)
                .order("-registerDate")
                .list();
    }

    public Long findCurrentMileage(Bike bike) {
        BikeEvent bikeEvent = filter("bike =", bike)
                .filter("status in", Arrays.asList(BikeEventStatus.NEW, BikeEventStatus.SYSTEM))
                .order("-timestamp")
                .limit(1)
                .first()
                .now();

        if (bikeEvent == null) {
            return null;
        }
        return bikeEvent.getMileage();
    }

    public Long findCurrentMth(Bike bike) {
        BikeEvent bikeEvent = filter("bike =", bike)
                .filter("status in", Arrays.asList(BikeEventStatus.NEW, BikeEventStatus.SYSTEM))
                .order("-timestamp")
                .limit(1)
                .first()
                .now();

        if (bikeEvent == null) {
            return null;
        }
        return bikeEvent.getMth();
    }

}
