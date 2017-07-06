package com.gruuf.model;

import com.github.rjeschke.txtmark.Processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BikeEventDescriptor {

    private UserLocale locale;
    private BikeEvent bikeEvent;

    public BikeEventDescriptor(UserLocale locale, BikeEvent bikeEvent) {
        this.locale = locale;
        this.bikeEvent = bikeEvent;
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

    public String getDescriptiveName() {
        return Processor.process(bikeEvent.getDescriptiveName());
    }

    public Date getRegisterDate() {
        return bikeEvent.getRegisterDate();
    }

    public Long getMileage() {
        return bikeEvent.getMileage();
    }

    public Long getMth() {
        return bikeEvent.getMth();
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
