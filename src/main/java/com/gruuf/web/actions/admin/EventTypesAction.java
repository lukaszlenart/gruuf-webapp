package com.gruuf.web.actions.admin;

import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.model.EventType;
import com.gruuf.services.EventTypes;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Tokens(Token.ADMIN)
public class EventTypesAction extends BaseAction {

    private static Logger LOG = LogManager.getLogger(EventTypesAction.class);

    private EventTypes eventTypes;

    public String execute() {
        return "event-types";
    }

    public List<EventType> getList() {
        List<EventType> types = eventTypes.list();
        LOG.debug("Found event types {}", types);

        return types;
    }

    @Inject
    public void setEventTypes(EventTypes eventTypes) {
        this.eventTypes = eventTypes;
    }
}
