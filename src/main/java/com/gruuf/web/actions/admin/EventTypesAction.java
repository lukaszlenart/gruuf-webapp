package com.gruuf.web.actions.admin;

import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.model.EventType;
import com.gruuf.services.BikeHistory;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Tokens(Token.ADMIN)
public class EventTypesAction extends BaseAction {

    private static Logger LOG = LogManager.getLogger(EventTypesAction.class);

    private BikeHistory bikeHistory;

    public String execute() {
        return "event-types";
    }

    public List<EventType> getList() {
        List<EventType> eventTypes = bikeHistory.listEventTypes();
        LOG.debug("Found event types {}", eventTypes);

        return eventTypes;
    }

    @Inject
    public void setBikeHistory(BikeHistory bikeHistory) {
        this.bikeHistory = bikeHistory;
    }
}
