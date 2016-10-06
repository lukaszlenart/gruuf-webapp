package com.gruuf.services;

import com.googlecode.objectify.ObjectifyService;
import com.gruuf.model.Bike;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.BikeEventStatus;
import com.gruuf.model.EventType;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BikeHistory extends Reindexable<BikeEvent> {

    public BikeHistory(Class<BikeEvent> type) {
        super(type);
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
                .filter("status in", Arrays.asList(BikeEventStatus.NEW, BikeEventStatus.SYSTEM))
                .order("-registerDate")
                .list();
    }

    public List<EventType> listEventTypes() {
        List<EventType> eventTypes = ObjectifyService
                .ofy()
                .load()
                .type(EventType.class)
                .list();

        Collections.sort(eventTypes, new Comparator<EventType>() {
            @Override
            public int compare(EventType o1, EventType o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return eventTypes;
    }

    public Long findCurrentMileage(Bike bike) {
        BikeEvent bikeEvent = filter("bike =", bike)
                .filter("status in", Arrays.asList(BikeEventStatus.NEW, BikeEventStatus.SYSTEM))
                .order("-registerDate")
                .limit(1)
                .first()
                .now();

        if (bikeEvent == null) {
            return null;
        }
        return bikeEvent.getMileage();
    }

}
