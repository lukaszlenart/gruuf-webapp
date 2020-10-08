package com.gruuf.model;

import org.apache.commons.lang3.StringUtils;

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
        String desc = eventType.getNames().get(locale);
        if (StringUtils.isEmpty(desc)) {
            desc = eventType.getNames().get(UserLocale.EN);
        }
        return desc;
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

    public String getRequesterFullName() {
        return eventType.getRequesterFullName();
    }
}
