package com.gruuf.web.actions.bike;

import com.gruuf.model.Bike;
import com.gruuf.model.BikeDetails;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.EventType;
import com.gruuf.services.BikeHistory;
import com.gruuf.services.Garage;
import com.gruuf.web.GruufActions;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;

import java.util.Date;
import java.util.List;

import static com.opensymphony.xwork2.Action.INPUT;

@Results(value = {
        @Result(name = "to-show-bike", location = "show", type = "redirectAction", params = {"bikeId", "${bikeId}"}),
        @Result(name = INPUT, location = "bike/show")
})
public class ShowAction extends BaseAction {

    private static Logger LOG = LogManager.getLogger(ShowAction.class);

    private String bikeId;
    private BikeDetails bikeDetails;

    private Garage garage;
    private BikeHistory bikeHistory;

    private String eventTypeId;
    private String descriptiveName;
    private Date registerDate;
    private Long mileage;

    private String bikeEventId;

    @SkipValidation
    public String execute() {
        Bike selectedBike = garage.get(bikeId);
        if (garage.canView(selectedBike, currentUser)) {
            loadBikeDetails(selectedBike);

            return "show";
        } else {
            LOG.warn("User {} cannot view bike {}", selectedBike, currentUser);
            return GruufActions.GARAGE;
        }
    }

    protected void loadBikeDetails(Bike selectedBike) {
        LOG.debug("User {} can view bike {}", selectedBike, currentUser);

        List<BikeEvent> events = bikeHistory.listByBike(selectedBike);
        LOG.debug("Found Bike Events for bike {}: {}", bikeId, events);

        bikeDetails = BikeDetails.create(selectedBike).withUser(currentUser).withHistory(events);
    }

    @Action("register-bike-event")
    public String registerBikeEvent() {
        LOG.debug("Registering new bike event for bike {}", bikeId);

        Bike selectedBike = garage.get(bikeId);
        if (garage.canView(selectedBike, currentUser)) {
            loadBikeDetails(selectedBike);
        }

        BikeEvent bikeEvent = BikeEvent.create(selectedBike)
                .withEventTypeId(eventTypeId)
                .withDescriptiveName(descriptiveName)
                .withRegisterDate(registerDate)
                .withMileage(mileage)
                .build();

        LOG.debug("Storing new Bike Event {}", bikeEvent);
        bikeHistory.put(bikeEvent);

        LOG.debug("Returning to show bike {}", bikeId);
        return "to-show-bike";
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

            bikeId = bikeEvent.getBike().getId();
        }

        LOG.debug("Returning to show bike {}", bikeId);
        return "to-show-bike";
    }

    @Inject
    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    @Inject
    public void setBikeHistory(BikeHistory bikeHistory) {
        this.bikeHistory = bikeHistory;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public String getBikeId() {
        return bikeId;
    }

    public BikeDetails getBikeDetails() {
        return bikeDetails;
    }

    public List<EventType> getEventTypesList() {
        return bikeHistory.listEventTypes();
    }

    public String getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(String eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public String getDescriptiveName() {
        return descriptiveName;
    }

    @StringLengthFieldValidator(minLength = "4")
    public void setDescriptiveName(String descriptiveName) {
        this.descriptiveName = descriptiveName;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    @RequiredFieldValidator
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Long getMileage() {
        return mileage;
    }

    @RequiredFieldValidator
    public void setMileage(Long mileage) {
        this.mileage = mileage;
    }

    public boolean isShowRegisterForm() {
        return !getActionErrors().isEmpty() || !getFieldErrors().isEmpty();
    }

    public void setBikeEventId(String bikeEventId) {
        this.bikeEventId = bikeEventId;
    }

}
