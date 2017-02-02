package com.gruuf.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.AlsoLoad;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.gruuf.web.GruufAuth;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class BikeEvent {

    @Id
    private String id;
    @Load
    @Index
    private Ref<Bike> bike;
    private List<Ref<EventType>> eventTypeIds;
    @Index
    private Date timestamp;
    private String descriptiveName;
    @Index
    private Date registerDate;
    private Long mileage;
    @Index
    private BikeEventStatus status = BikeEventStatus.NEW;
    private Ref<User> registeredBy;

    private BikeEvent() {
    }

    public BikeEvent(Bike bike) {
        this.bike = Ref.create(bike);
    }

    public void migrate(@AlsoLoad("eventTypeId") Ref<EventType> eventTypeId) {
        if (eventTypeIds == null) {
            eventTypeIds = new ArrayList<>();
        }
        if (eventTypeId != null && !eventTypeIds.contains(eventTypeId)) {
            eventTypeIds.add(eventTypeId);
        }
    }

    public String getId() {
        return id;
    }

    public Bike getBike() {
        return bike.get();
    }

    public List<EventType> getEventTypes() {
        List<EventType> eventTypes = new ArrayList<>();
        for (Ref<EventType> eventTypeId : eventTypeIds) {
            eventTypes.add(eventTypeId.get());
        }
        return eventTypes;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getDescriptiveName() {
        return descriptiveName;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public Long getMileage() {
        return mileage;
    }

    public BikeEventStatus getStatus() {
        return status;
    }

    public User getAuthor() {
        return registeredBy.get();
    }

    public boolean isDeletable() {
        return status.isDeletable();
    }

    @Override
    public String toString() {
        return "BikeEvent{" +
                "id='" + id + '\'' +
                ", bike=" + bike +
                ", eventTypeIds=" + eventTypeIds +
                ", timestamp=" + timestamp +
                ", descriptiveName='" + descriptiveName + '\'' +
                ", registerDate=" + registerDate +
                ", mileage=" + mileage +
                ", status=" + status +
                ", registeredBy=" + registeredBy +
                '}';
    }

    public static BikeEventBuilder create(Bike bike, User registeredBy) {
        return new BikeEventBuilder(bike, registeredBy);
    }

    public BikeEvent markAsDeleted() {
        this.status = BikeEventStatus.DELETED;
        this.timestamp = new Date();
        return this;
    }

    public static class BikeEventBuilder {

        private final BikeEvent target;

        private BikeEventBuilder(Bike bike, User registeredBy) {
            target = new BikeEvent(bike);
            target.id = GruufAuth.generateUUID();
            target.timestamp = new Date();
            target.status = BikeEventStatus.NEW;
            target.registeredBy = Ref.create(registeredBy);
        }

        public BikeEventBuilder withEventTypeId(Set<String> eventTypeIds) {
            target.eventTypeIds = new ArrayList<>();
            for (String eventTypeId : eventTypeIds) {
                target.eventTypeIds.add(Ref.create(Key.create(EventType.class, eventTypeId)));
            }
            return this;
        }

        public BikeEventBuilder withDescriptiveName(String descriptiveName) {
            target.descriptiveName = descriptiveName;
            return this;
        }

        public BikeEventBuilder withRegisterDate(Date registerDate) {
            target.registerDate = registerDate;
            return this;
        }

        public BikeEventBuilder withMileage(Long mileage) {
            target.mileage = mileage;
            return this;
        }

        public BikeEventBuilder markAsSystem() {
            target.status = BikeEventStatus.SYSTEM;
            return this;
        }

        public BikeEvent build() {
            return target;
        }
    }
}
