package com.gruuf.web.actions.bike;

import com.gruuf.auth.BikeRestriction;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.BikeEventStatus;
import com.gruuf.web.GlobalResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

import static com.opensymphony.xwork2.Action.INPUT;

@Result(name = INPUT, location = "bike/bike-delete")
@BikeRestriction
public class BikeDeleteAction extends BaseBikeAction {

    private static final Logger LOG = LogManager.getLogger(BikeDeleteAction.class);

    public String execute() {
        LOG.info("User wants to delete bike {}", selectedBike.getId());
        return INPUT;
    }

    @Action("bike-delete-confirm")
    public String deleteConfirm() {
        List<BikeEvent> bikeEvents = bikeHistory.listByBike(selectedBike, BikeEventStatus.values());

        LOG.info("Deleting all historical events");
        for (BikeEvent bikeEvent : bikeEvents) {
            bikeHistory.drop(bikeEvent.getId());
        }

        LOG.info("Dropping bike");
        garage.drop(selectedBike.getId());

        return GlobalResult.GARAGE;
    }

}
