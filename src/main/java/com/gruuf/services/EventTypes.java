package com.gruuf.services;

import com.gruuf.model.EventType;
import com.gruuf.model.EventTypeStatus;

import java.util.Arrays;
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
}
