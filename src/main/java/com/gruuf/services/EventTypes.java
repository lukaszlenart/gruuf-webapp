package com.gruuf.services;

import com.googlecode.objectify.Ref;
import com.gruuf.model.EventType;
import com.gruuf.model.EventTypeDescriptor;
import com.gruuf.model.EventTypeStatus;
import com.gruuf.model.User;

import java.util.ArrayList;
import java.util.Collections;
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

    public EventType getTransferEventType() {
        return findUniqueBy("status =", EventTypeStatus.TRANSFER);
    }

    private List<EventType> getEventTypes(EventTypeStatus status) {
        return filter("status =", status)
                .filter("approved =", Boolean.TRUE)
                .list();
    }

    public List<EventTypeDescriptor> listApproved(User currentUser) {
        List<EventType> approvedEvents = getEventTypes(EventTypeStatus.NORMAL);
        approvedEvents.addAll(getEventTypes(EventTypeStatus.IMPORTANT));

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
        approved.sort((desc1, desc2) -> {
            if (desc1.getName() == null) {
                return 0;
            }
            return desc1.getName().compareTo(desc2.getName());
        });
    }
}
