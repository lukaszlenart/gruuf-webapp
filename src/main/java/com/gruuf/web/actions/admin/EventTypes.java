package com.gruuf.web.actions.admin;

import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.model.EventType;
import com.gruuf.services.BikeHistory;
import com.gruuf.web.actions.BaseAction;
import com.gruuf.web.interceptors.BikeHistoryAware;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Tokens(Token.ADMIN)
public class EventTypes extends BaseAction implements BikeHistoryAware {

    private static Logger LOG = LogManager.getLogger(EventTypes.class);

    private BikeHistory bikeHistory;

    public String execute() {
        return "event-types";
    }

    public List<EventType> getList() {
        List<EventType> eventTypes = bikeHistory.listEventTypes();
        LOG.debug("Found event types {}", eventTypes);

        return eventTypes;
    }

    @Override
    public void setBikeHistory(BikeHistory bikeHistory) {
        this.bikeHistory = bikeHistory;
    }
}
