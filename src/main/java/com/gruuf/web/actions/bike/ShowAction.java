package com.gruuf.web.actions.bike;

import com.gruuf.auth.BikeRestriction;
import com.gruuf.model.BikeDetails;
import com.gruuf.model.BikeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;

import static com.opensymphony.xwork2.Action.INPUT;

@Result(name = INPUT, location = "bike/show")
@BikeRestriction
public class ShowAction extends BaseBikeAction {

    private static Logger LOG = LogManager.getLogger(ShowAction.class);

    private String bikeEventId;

    @SkipValidation
    public String execute() {
        return "show";
    }

    @SkipValidation
    @Action("delete-bike-event")
    public String deleteBikeEvent() {

        LOG.debug("Deleting bike event {}", bikeEventId);
        BikeEvent bikeEvent = bikeHistory.get(bikeEventId);

        if (bikeEvent != null) {
            LOG.debug("Marking BikeEvent {} as deleted", bikeEvent);
            bikeEvent = bikeEvent.markAsDeleted();
            bikeHistory.put(bikeEvent);
        }

        LOG.debug("Returning to show bike {}", getBikeId());
        return TO_SHOW_BIKE;
    }

    public BikeDetails getBikeDetails() {
        return loadBikeDetails();
    }

    public void setBikeEventId(String bikeEventId) {
        this.bikeEventId = bikeEventId;
    }

}
