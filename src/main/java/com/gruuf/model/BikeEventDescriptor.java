package com.gruuf.model;

import com.github.rjeschke.txtmark.Processor;

import java.util.Date;

public class BikeEventDescriptor {

    private BikeEvent bikeEvent;

    public BikeEventDescriptor(BikeEvent bikeEvent) {
        this.bikeEvent = bikeEvent;
    }

    public String getId() {
        return bikeEvent.getId();
    }

    public Bike getBike() {
        return bikeEvent.getBike();
    }

    public EventType getEventType() {
        return bikeEvent.getEventType();
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

    public BikeEventStatus getStatus() {
        return bikeEvent.getStatus();
    }

    public User getAuthor() {
        return bikeEvent.getAuthor();
    }

    public boolean isDeletable() {
        return bikeEvent.isDeletable();
    }

}
