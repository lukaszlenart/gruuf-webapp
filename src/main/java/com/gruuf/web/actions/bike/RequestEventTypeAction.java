package com.gruuf.web.actions.bike;

import com.gruuf.model.EventType;
import com.gruuf.model.EventTypeStatus;
import com.gruuf.services.EventTypes;
import com.gruuf.services.MailBox;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;

import java.util.Set;

import static com.opensymphony.xwork2.Action.INPUT;

@Results({
        @Result(name = BaseAction.TO_INPUT, location = "request-event-type", type = "redirectAction"),
        @Result(name = INPUT, location = "bike/request-event-type-input")
})
@InterceptorRef("defaultWithMessages")
public class RequestEventTypeAction extends BaseAction {

    private static Logger LOG = LogManager.getLogger(RequestEventTypeAction.class);

    private EventTypes eventTypes;
    private MailBox mailBox;

    private String name;
    private EventTypeStatus status = EventTypeStatus.NORMAL;

    @SkipValidation
    public String execute() {
        return INPUT;
    }

    @Action("request-event-type-submit")
    public String requestEventType() {
        LOG.debug("Requesting new event type {}-{}", name, status);

        EventType eventType = EventType.create()
                .withName(currentUser.getUserLocale(),name)
                .withRequestedBy(currentUser)
                .withStatus(status)
                .build();

        eventType = eventTypes.put(eventType);

        LOG.debug("New event type created: {}", eventType);
        mailBox.notifyAdmin("New Event Type request", "A new Event Type was requested", eventType);
        addActionMessage(getText("general.newRequestSubmitted"));

        return "to-input";
    }

    @Inject
    public void setEventTypes(EventTypes eventTypes) {
        this.eventTypes = eventTypes;
    }

    @Inject
    public void setMailBox(MailBox mailBox) {
        this.mailBox = mailBox;
    }

    public String getName() {
        return name;
    }

    @RequiredStringValidator(key = "general.fieldIsRequired")
    @StringLengthFieldValidator(minLength = "2", key = "general.minLength")
    public void setName(String name) {
        this.name = name;
    }

    public EventTypeStatus getStatus() {
        return status;
    }

    @RequiredFieldValidator(key = "general.fieldIsRequired")
    public void setStatus(EventTypeStatus status) {
        this.status = status;
    }

    public Set<EventTypeStatus> getAvailableStatuses() {
        return EventTypeStatus.nonSystem();
    }
}