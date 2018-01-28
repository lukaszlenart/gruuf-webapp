package com.gruuf.web.actions.bike;

import com.gruuf.auth.BikeRestriction;
import com.gruuf.model.BikeDetails;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.EventType;
import com.opensymphony.xwork2.Preparable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;

import static com.opensymphony.xwork2.Action.INPUT;

@Result(name = INPUT, location = "bike/history")
@BikeRestriction
public class HistoryAction extends BaseBikeAction implements Preparable {

    private static Logger LOG = LogManager.getLogger(HistoryAction.class);

    private String bikeEventId;
    private BikeDetails bikeDetails;

    @SkipValidation
    public String execute() {
        return "history";
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

    @SkipValidation
    @Action("approve-bike-event")
    public String approveBikeEvent() {

        LOG.debug("Approving bike event {}", bikeEventId);
        BikeEvent bikeEvent = bikeHistory.get(bikeEventId);

        if (bikeEvent != null) {
            EventType mileageEventType = eventTypes.getMileageEventType();
            EventType mthEventType = eventTypes.getMthEventType();

            if (isSystemEvent(bikeEvent, mileageEventType, mthEventType)) {
                LOG.debug("Marking BikeEvent {} as system", bikeEvent);
                bikeEvent = bikeEvent.markAsSystem();
            } else {
                LOG.debug("Marking BikeEvent {} as new", bikeEvent);
                bikeEvent = bikeEvent.markAsNew();
            }

            bikeHistory.put(bikeEvent);
        }

        LOG.debug("Returning to show bike {}", getBikeId());
        return TO_SHOW_BIKE;
    }

    private boolean isSystemEvent(BikeEvent bikeEvent, EventType mileageEventType, EventType mthEventType) {
        return bikeEvent.getEventTypes().contains(mileageEventType) || bikeEvent.getEventTypes().contains(mthEventType);
    }

    public BikeDetails getBikeDetails() {
        return bikeDetails;
    }

    public void setBikeEventId(String bikeEventId) {
        this.bikeEventId = bikeEventId;
    }

    @Override
    public void prepare() throws Exception {
        bikeDetails = loadBikeDetails();
    }
}
