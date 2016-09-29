package com.gruuf.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.gruuf.web.GruufAuth;

import java.util.Date;

@Entity
public class EventType {
    @Id
    private String id;
    private String name;
    private Date created;
    private EventTypeStatus status = EventTypeStatus.NORMAL;

    private EventType() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCreated() {
        return created;
    }

    public EventTypeStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "EventType{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", created=" + created +
                ", status=" + status +
                '}';
    }

    public static EventTypeCreator create() {
        return new EventTypeCreator(GruufAuth.generateUUID());
    }

    public static EventTypeCreator create(EventType eventType) {
        return new EventTypeCreator(eventType.getId())
                .withName(eventType.getName())
                .witStatus(eventType.getStatus());
    }

    public static class EventTypeCreator {

        private final EventType target;

        private EventTypeCreator(String id) {
            target = new EventType();
            target.id = id;
            target.created = new Date();
        }

        public EventTypeCreator withName(String name) {
            target.name = name;
            return this;
        }

        public EventType build() {
            return target;
        }

        public EventTypeCreator witStatus(EventTypeStatus status) {
            target.status = status;
            return this;
        }
    }
}
