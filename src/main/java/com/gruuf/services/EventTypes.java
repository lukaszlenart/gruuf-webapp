package com.gruuf.services;

import com.googlecode.objectify.Ref;
import com.gruuf.model.EventType;
import com.gruuf.model.EventTypeDescriptor;
import com.gruuf.model.EventTypeStatus;
import com.gruuf.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EventTypes extends Reindexable<EventType> {

    public EventTypes() {
        super(EventType.class);
    }

    @Override
    protected boolean shouldReindex() {
        // change to true when migrating data
        return false;
    }

    public EventType getMileageEventType() {
        return findUniqueBy("status =", EventTypeStatus.MILEAGE);
    }

    public EventType getMthEventType() {
        return findUniqueBy("status =", EventTypeStatus.MTH);
    }

    public List<EventTypeDescriptor> listApproved(User currentUser) {
        List<EventType> approvedEvents = filter("status in", Arrays.asList(EventTypeStatus.NORMAL, EventTypeStatus.IMPORTANT))
                .filter("approved = ", Boolean.TRUE)
                .list();
        List<EventType> unapprovedEvents = filter("approved =", Boolean.FALSE).list();

        List<EventTypeDescriptor> approved = new ArrayList<>();

        for (EventType approvedEvent : approvedEvents) {
            approved.add(new EventTypeDescriptor(currentUser.getUserLocale(), approvedEvent));
        }

        for (EventType eventType : unapprovedEvents) {
            if (Ref.create(currentUser).equivalent(eventType.getRequestedBy())) {
                approved.add(new EventTypeDescriptor(currentUser.getUserLocale(), eventType));
            }
        }

        sort(approved);

        return approved;
    }

    public List<EventTypeDescriptor> listAll(User currentUser) {
        List<EventTypeDescriptor> approved = new ArrayList<>();

        for (EventType approvedEvent : list()) {
            approved.add(new EventTypeDescriptor(currentUser.getUserLocale(), approvedEvent));
        }

        sort(approved);

        return approved;
    }

    private void sort(List<EventTypeDescriptor> approved) {
        Collections.sort(approved, new Comparator<EventTypeDescriptor>() {
            @Override
            public int compare(EventTypeDescriptor o1, EventTypeDescriptor o2) {
                if (o1.getName() == null) {
                    return 0;
                }
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

}
