package com.gruuf.model;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.AlsoLoad;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Stringify;
import com.googlecode.objectify.stringifier.Stringifier;
import com.gruuf.web.GruufAuth;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class UserLocaleStringifier implements Stringifier<UserLocale> {

    @Override
    public String toString(UserLocale userLocale) {
        return userLocale.name();
    }

    @Override
    public UserLocale fromString(String name) {
        return UserLocale.valueOf(name);
    }
}

@Entity
public class EventType {
    @Id
    private String id;

    @Stringify(UserLocaleStringifier.class)
    private Map<UserLocale, String> names;
    @Index
    private EventTypeStatus status = EventTypeStatus.NORMAL;

    @Index
    private boolean approved = true;
    private Ref<User> requestedBy;
    private Date created;

    private EventType() {
    }

    public void migrate(@AlsoLoad("name") String name) {
        if (names == null) {
            names = new HashMap<>();
        }
        if (!names.containsKey(UserLocale.PL)) {
            names.put(UserLocale.PL, name);
        }
    }

    public String getId() {
        return id;
    }

    public Map<UserLocale, String> getNames() {
        return names;
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
                ", names='" + names + '\'' +
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
                .withNames(eventType.getNames())
                .withStatus(eventType.getStatus());
    }

    public static class EventTypeCreator {

        private final EventType target;

        private EventTypeCreator(String id) {
            target = new EventType();
            target.id = id;
            target.created = new Date();
        }

        public EventType build() {
            return target;
        }

        public EventTypeCreator withNames(Map<UserLocale, String> names) {
            target.names = names;
            return this;
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

        public EventTypeCreator withName(UserLocale locale, String name) {
            target.names.put(locale, name);
            return this;
        }
    }
}
