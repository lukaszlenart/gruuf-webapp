package com.gruuf.services;

import com.gruuf.model.EventType;
import com.gruuf.model.EventTypeStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EventTypes extends Reindexable<EventType> {

    public EventTypes(Class<EventType> type) {
        super(type);
    }

    public EventType getMileageEventType() {
        return findUniqueBy("status =", EventTypeStatus.MILEAGE);
    }

    public List<EventType> listAllowedEventTypes() {
        return filter("status in", Arrays.asList(EventTypeStatus.NORMAL, EventTypeStatus.IMPORTANT))
                .list();
    }

    @Override
    public List<EventType> list() {
        List<EventType> eventTypes = super.list();

        Collections.sort(eventTypes, new Comparator<EventType>() {
            @Override
            public int compare(EventType o1, EventType o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return eventTypes;
    }
}
