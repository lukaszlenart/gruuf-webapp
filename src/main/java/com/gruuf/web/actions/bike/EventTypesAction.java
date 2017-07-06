package com.gruuf.web.actions.bike;

import com.gruuf.model.EventTypeDescriptor;
import com.gruuf.services.EventTypes;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

import static com.gruuf.web.actions.BaseAction.JSON;

@InterceptorRefs({
        @InterceptorRef("gruufDefaultDev"),
        @InterceptorRef(value = "json")
})
@Result(type = JSON, params = {"root", "list"})
public class EventTypesAction extends BaseBikeAction {

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
