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
        Long mileage = null;

        List<BikeEvent> bikeEvents = filter("bike =", bike)
                .filter("status in", Arrays.asList(BikeEventStatus.NEW, BikeEventStatus.SYSTEM))
                .order("-timestamp")
                .list();

        for (BikeEvent event : bikeEvents) {
            if (mileage == null || mileage.compareTo(event.getMileage()) < 0) {
                mileage = event.getMileage();
            }
        }

        return mileage;
    }

    public Long findCurrentMth(Bike bike) {
        Long mth = null;

        List<BikeEvent> bikeEvents = filter("bike =", bike)
                .filter("status in", Arrays.asList(BikeEventStatus.NEW, BikeEventStatus.SYSTEM))
                .order("-timestamp")
                .list();

        for (BikeEvent event : bikeEvents) {
            if (mth == null || mth.compareTo(event.getMth()) < 0) {
                mth = event.getMth();
            }
        }

        return mth;
    }

}
