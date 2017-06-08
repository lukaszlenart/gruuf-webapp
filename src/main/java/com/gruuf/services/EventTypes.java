package com.gruuf.services;

import com.googlecode.objectify.Ref;
import com.gruuf.model.EventType;
import com.gruuf.model.EventTypeStatus;
import com.gruuf.model.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EventTypes extends Reindexable<EventType> {

    public EventTypes() {
        super(EventType.class);
    }

    public EventType getMileageEventType() {
        return findUniqueBy("status =", EventTypeStatus.MILEAGE);
    }

    public EventType getMthEventType() {
        return findUniqueBy("status =", EventTypeStatus.MTH);
    }

    public List<EventType> listApproved(User currentUser) {
        List<EventType> approved = filter("status in", Arrays.asList(EventTypeStatus.NORMAL, EventTypeStatus.IMPORTANT))
                .filter("approved = ", Boolean.TRUE)
                .list();
        List<EventType> unapproved = filter("approved =", Boolean.FALSE).list();

        for (EventType eventType : unapproved) {
            if (eventType.getRequestedBy().equivalent(Ref.create(currentUser))) {
                approved.add(eventType);
            }
        }

        Collections.sort(approved, new Comparator<EventType>() {
            @Override
            public int compare(EventType o1, EventType o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return approved;

    }
}
