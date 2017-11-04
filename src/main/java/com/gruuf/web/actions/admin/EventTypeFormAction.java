package com.gruuf.web.actions.admin;

import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.model.EventType;
import com.gruuf.model.EventTypeStatus;
import com.gruuf.model.UserLocale;
import com.gruuf.services.EventTypes;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.Key;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;

import java.util.Map;
import java.util.Set;

import static com.opensymphony.xwork2.Action.INPUT;

@Results({
        @Result(name = "to-event-types", location = "event-types", type = "redirectAction"),
        @Result(name = INPUT, location = "admin/event-type-input")
})
@Tokens(Token.ADMIN)
public class EventTypeFormAction extends BaseAction {

    private static Logger LOG = LogManager.getLogger(EventTypeFormAction.class);

    private EventTypes eventTypes;

    private String eventTypeId;

    @Key(UserLocale.class)
    private Map<UserLocale, String> names;
    private EventTypeStatus status = EventTypeStatus.NORMAL;

    @SkipValidation
    public String execute() {
        if (StringUtils.isEmpty(eventTypeId)) {
            LOG.debug("Showing event type create form");
        } else {
            LOG.debug("Showing event type edit form");
            EventType eventType = eventTypes.get(eventTypeId);

            names = eventType.getNames();
            status = eventType.getStatus();
        }
        return INPUT;
    }

    @Action("update-event-type")
    public String updateEventType() {
        if (StringUtils.isEmpty(eventTypeId)) {
            LOG.debug("Creating new event type of name {}", names);

            EventType eventType = EventType.create()
                    .withNames(names)
                    .withRequestedBy(currentUser)
                    .withApproved()
                    .withStatus(status)
                    .build();
            EventType result = eventTypes.put(eventType);

            LOG.debug("New event type created: {}", result);
        } else {
            LOG.debug("Updating existing event type [{}] with names [{}]", eventTypeId, names);
            EventType eventType = eventTypes.get(eventTypeId);
            eventType = EventType.create(eventType)
                    .withNames(names)
                    .withRequestedBy(currentUser)
                    .withApproved()
                    .withStatus(status)
                    .build();
            eventTypes.put(eventType);
        }

        return "to-event-types";
    }

    @Inject
    public void setEventTypes(EventTypes eventTypes) {
        this.eventTypes = eventTypes;
    }

    public String getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(String eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public Map<UserLocale, String> getNames() {
        return names;
    }

    public void setNames(Map<UserLocale, String> names) {
        this.names = names;
    }

    public EventTypeStatus getStatus() {
        return status;
    }

    public void setStatus(EventTypeStatus status) {
        this.status = status;
    }

    public Set<EventTypeStatus> getAvailableStatuses() {
        return EventTypeStatus.all();
    }
}