package com.gruuf.web.actions.admin;

import com.googlecode.objectify.Key;
import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.model.EventType;
import com.gruuf.model.EventTypeStatus;
import com.gruuf.services.BikeHistory;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;

import java.util.Set;

import static com.opensymphony.xwork2.Action.INPUT;

@Results({
        @Result(name = "to-event-types", location = "event-types", type = "redirectAction"),
        @Result(name = INPUT, location = "admin/add-event-type-input")
})
@Tokens(Token.ADMIN)
public class AddEventTypeAction extends BaseAction {

    private static Logger LOG = LogManager.getLogger(AddEventTypeAction.class);

    private BikeHistory bikeHistory;

    private String name;
    private EventTypeStatus status;

    @SkipValidation
    public String execute() {
        LOG.debug("Showing event type create form");
        return INPUT;
    }

    @Action("update-event-type")
    public String updateEventType() {
        LOG.debug("Creating new event type of name {}", name);

        EventType eventType = EventType.create().withName(name).witStatus(status).build();
        Key<EventType> result = bikeHistory.addEventType(eventType);

        LOG.debug("New event type created: {}", result);

        return "to-event-types";
    }

    @Inject
    public void setBikeHistory(BikeHistory bikeHistory) {
        this.bikeHistory = bikeHistory;
    }

    public String getName() {
        return name;
    }

    @StringLengthFieldValidator(minLength = "3")
    public void setName(String name) {
        this.name = name;
    }

    public EventTypeStatus getStatus() {
        return status;
    }

    @RequiredFieldValidator
    public void setStatus(EventTypeStatus status) {
        this.status = status;
    }

    public Set<EventTypeStatus> getStatuses() {
        return EventTypeStatus.all();
    }
}