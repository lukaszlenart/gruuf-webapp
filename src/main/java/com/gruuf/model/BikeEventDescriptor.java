package com.gruuf.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BikeEventDescriptor {

    private final UserLocale locale;
    private final BikeEvent bikeEvent;
    private final Long currentMileage;
    private final Long currentMth;

    public BikeEventDescriptor(UserLocale locale, BikeEvent bikeEvent, Long currentMileage, Long currentMth) {
        this.locale = locale;
        this.bikeEvent = bikeEvent;
        this.currentMileage = currentMileage;
        this.currentMth = currentMth;
    }

    public BikeEventDescriptor(UserLocale locale, BikeEvent bikeEvent) {
        this(locale, bikeEvent, null, null);
    }

    public String getId() {
        return bikeEvent.getId();
    }

    public Bike getBike() {
        return bikeEvent.getBike();
    }

    public List<EventTypeDescriptor> getEventTypes() {
        List<EventTypeDescriptor> eventTypes = new ArrayList<>();
        for (EventType eventType : bikeEvent.getEventTypes()) {
            eventTypes.add(new EventTypeDescriptor(locale, eventType));
        }
        return eventTypes;
    }

    public Date getTimestamp() {
        return bikeEvent.getTimestamp();
    }

    public Markdown getDescription() {
        return bikeEvent.getDescription();
    }

    public Date getRegisterDate() {
        return bikeEvent.getRegisterDate();
    }

    public Long getMileage() {
        return bikeEvent.getMileage();
    }

    public boolean isCurrentMileage() {
        return bikeEvent.isMileage() && currentMileage != null && bikeEvent.getStatus() != BikeEventStatus.SYSTEM;
    }

    public Long getCurrentMileage() {
        Long mileage = bikeEvent.getMileage();
        if (isCurrentMileage()) {
            return currentMileage - mileage;
        }
        return mileage;
    }

    public boolean isCurrentMth() {
        return bikeEvent.isMth() && currentMth != null && bikeEvent.getStatus() != BikeEventStatus.SYSTEM;
    }

    public Long getCurrentMth() {
        Long mth = bikeEvent.getMth();
        if (isCurrentMth()) {
            return currentMth - mth;
        }
        return mth;
    }

    public BikeEventStatus getStatus() {
        return bikeEvent.getStatus();
    }

    public User getAuthor() {
        return bikeEvent.getAuthor();
    }

    public boolean isDeletable() {
        return bikeEvent.isDeletable();
    }

    public boolean isEditable() {
        return bikeEvent.isEditable();
    }

}
