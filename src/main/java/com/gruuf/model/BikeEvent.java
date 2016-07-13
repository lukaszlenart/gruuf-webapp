package com.gruuf.model;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

import java.util.Date;

@Entity
public class BikeEvent {

    @Id
    private String id;
    @Load
    private Ref<Bike> bike;
    @Load
    private Ref<EventType> eventTypeId;
    private Date timestamp;

    public String getId() {
        return id;
    }

    public Bike getBike() {
        return bike.get();
    }

    public EventType getEventType() {
        return eventTypeId.get();
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
