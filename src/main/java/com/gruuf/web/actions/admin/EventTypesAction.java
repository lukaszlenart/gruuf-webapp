package com.gruuf.web.actions.admin;

import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.model.EventType;
import com.gruuf.services.EventTypes;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.ArrayList;
import java.util.List;

@Tokens(Token.ADMIN)
@Result(name = "json", type = "json", params = {"root", "list"})
public class EventTypesAction extends BaseAction {

    private static Logger LOG = LogManager.getLogger(EventTypesAction.class);

    private EventTypes eventTypes;

    public String execute() {
        return "event-types";
    }

    @Action("event-types-json")
    public String eventTypesJson() {
        return "json";
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
