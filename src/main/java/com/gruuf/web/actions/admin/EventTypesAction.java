package com.gruuf.web.actions.admin;

import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.model.EventTypeDescriptor;
import com.gruuf.services.EventTypes;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

import static com.gruuf.web.actions.BaseAction.JSON;

@Tokens(Token.ADMIN)
@Result(name = JSON, type = JSON, params = {"root", "list"})
public class EventTypesAction extends BaseAction {

    private EventTypes eventTypes;

    public String execute() {
        return "event-types";
    }

    @Action("event-types-json")
    public String eventTypesJson() {
        return JSON;
    }

    public List<EventTypeDescriptor> getList() {
        return eventTypes.listAll(currentUser);
    }

    @Inject
    public void setEventTypes(EventTypes eventTypes) {
        this.eventTypes = eventTypes;
    }
}
