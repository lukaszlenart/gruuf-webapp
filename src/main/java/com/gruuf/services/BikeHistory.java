package com.gruuf.services;

import com.gruuf.model.Bike;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.BikeEventStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BikeHistory extends Reindexable<BikeEvent> {

    public BikeHistory() {
        super(BikeEvent.class);
    }

    @Override
    protected boolean shouldReindex() {
        // change to true when migrating data
        return false;
    }

    public List<BikeEvent> listByBike(Bike bike, BikeEventStatus... statuses) {
        return getBikeEvents(bike, statuses);
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

        List<BikeEvent> bikeEvents = getBikeEvents(bike, BikeEventStatus.NEW, BikeEventStatus.SYSTEM);

        for (BikeEvent event : bikeEvents) {
            if (mileage == null || (event.getMileage() != null && mileage.compareTo(event.getMileage()) < 0)) {
                mileage = event.getMileage();
            }
        }

        return mileage;
    }

    public Long findCurrentMth(Bike bike) {
        Long mth = null;

        List<BikeEvent> bikeEvents = getBikeEvents(bike, BikeEventStatus.NEW, BikeEventStatus.SYSTEM);

        for (BikeEvent event : bikeEvents) {
            if (mth == null || (event.getMth() != null && mth.compareTo(event.getMth()) < 0)) {
                mth = event.getMth();
            }
        }

        return mth;
    }

    private List<BikeEvent> getBikeEvents(Bike bike, BikeEventStatus... statuses) {
        List<BikeEvent> bikeEvents = new ArrayList<>();
        for (BikeEventStatus status : statuses) {
            bikeEvents.addAll(getBikeEvents(bike, status));
        }

        Comparator<BikeEvent> comparator = Collections.reverseOrder(Comparator.comparing(BikeEvent::getRegisterDate));

        return bikeEvents.stream().sorted(comparator).collect(Collectors.toList());
    }

    private List<BikeEvent> getBikeEvents(Bike bike, BikeEventStatus status) {
        return filter("bike =", bike)
                .filter("status =", status)
                .list();
    }

}
