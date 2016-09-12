package com.gruuf.web.actions.admin;

import com.googlecode.objectify.Key;
import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.model.EventType;
import com.gruuf.services.BikeHistory;
import com.gruuf.web.actions.BaseAction;
import com.gruuf.web.interceptors.BikeHistoryAware;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;

@Tokens(Token.ADMIN)
@Result(name = "to-event-types", location = "event-types", type = "redirectAction")
public class AddEventType extends BaseAction implements BikeHistoryAware {

    private static Logger LOG = LogManager.getLogger(AddEventType.class);

    private BikeHistory bikeHistory;

    private String name;

    @SkipValidation
    public String execute() {
        LOG.debug("Showing event type create form");
        return INPUT;
    }

    @Action("update-event-type")
    public String updateEventType() {
        LOG.debug("Creating new event type of name {}", name);

        EventType eventType = EventType.create().withName(name).build();
        Key<EventType> result = bikeHistory.addEventType(eventType);

        LOG.debug("New event type created: {}", result);

        return "to-event-types";
    }

    @Override
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

}