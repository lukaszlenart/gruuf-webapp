package com.gruuf.model;

import com.googlecode.objectify.Ref;

import java.util.Date;
import java.util.Map;

public class EventTypeDescriptor {
    private final UserLocale locale;
    private final EventType eventType;

    public EventTypeDescriptor(UserLocale locale, EventType eventType) {
        this.locale = locale;
        this.eventType = eventType;
    }

    public String getId() {
        return eventType.getId();
    }

    public Map<UserLocale, String> getNames() {
        return eventType.getNames();
    }

    public String getName() {
        if (eventType.getNames() == null) {
            return "";
        }
        return eventType.getNames().get(locale);
    }

    public EventTypeStatus getStatus() {
        return eventType.getStatus();
    }

    public Date getCreated() {
        return eventType.getCreated();
    }

    public boolean isApproved() {
        return eventType.isApproved();
    }

    public Ref<User> getRequestedBy() {
        return eventType.getRequestedBy();
    }

    public String getRequesterFullName() {
        return eventType.getRequesterFullName();
    }
}
