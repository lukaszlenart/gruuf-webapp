package com.gruuf.model;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.gruuf.web.GruufAuth;

import java.util.Date;

@Entity
public class EventType {
    @Id
    private String id;
    private String name;
    private Date created;
    @Index
    private EventTypeStatus status = EventTypeStatus.NORMAL;

    @Index
    private boolean approved = false;
    private Ref<User> requestedBy;

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

    public boolean isApproved() {
        return approved;
    }

    public Ref<User> getRequestedBy() {
        return requestedBy;
    }

    public String getRequesterFullName() {
        if (requestedBy != null) {
            return requestedBy.get().getFullName();
        }
        return "";
    }

    @Override
    public String toString() {
        return "EventType{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", created=" + created +
                ", status=" + status +
                ", approved=" + approved +
                ", requestedBy=" + requestedBy +
                '}';
    }

    public static EventTypeCreator create() {
        return new EventTypeCreator(GruufAuth.generateUUID());
    }

    public static EventTypeCreator create(EventType eventType) {
        return new EventTypeCreator(eventType.getId())
                .withName(eventType.getName())
                .withStatus(eventType.getStatus());
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

        public EventTypeCreator withStatus(EventTypeStatus status) {
            target.status = status;
            return this;
        }

        public EventTypeCreator withRequestedBy(User requester) {
            if (target.requestedBy == null) {
                target.requestedBy = Ref.create(requester);
            }
            return this;
        }

        public EventTypeCreator withApproved() {
            target.approved = true;
            return this;
        }
    }
}
