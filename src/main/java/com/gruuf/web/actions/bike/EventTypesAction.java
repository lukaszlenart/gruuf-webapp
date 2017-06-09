package com.gruuf.web.actions.bike;

import com.gruuf.model.EventType;
import com.gruuf.model.EventTypeDescriptor;
import com.gruuf.services.EventTypes;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

@InterceptorRefs({
        @InterceptorRef("gruufDefaultDev"),
        @InterceptorRef(value = "json")
})
@Result(type = "json", params = {"root", "list"})
public class EventTypesAction extends BaseBikeAction {

    private static final Logger LOG = LogManager.getLogger(EventTypesAction.class);

    private EventTypes eventTypes;

    @Action("event-types")
    public String execute() throws Exception {
        return SUCCESS;
    }

    @Inject
    public void setEventTypes(EventTypes eventTypes) {
        this.eventTypes = eventTypes;
    }

    public List<EventTypeDescriptor> getList() {
        return eventTypes.listApproved(currentUser);
    }

}
