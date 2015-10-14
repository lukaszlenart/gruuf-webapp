package me.rbw.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;

import java.util.Date;

@Entity
public class BikeEvent {

    @Id
    private String id;
    @Parent()
    private Key<Bike> bike;
    @Load
    private Ref<EventType> eventTypeId;
    private Date timestamp;

    public String getId() {
        return id;
    }

    public Key<Bike> getBike() {
        return bike;
    }

    public EventType getEventType() {
        return eventTypeId.get();
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
