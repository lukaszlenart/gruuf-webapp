package com.gruuf.web.actions.bike;

import com.gruuf.auth.BikeRestriction;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.EventType;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;

import java.util.Date;
import java.util.List;

import static com.opensymphony.xwork2.Action.INPUT;

@Result(name = INPUT, location = "bike/new-bike-event")
@BikeRestriction
public class BikeEventAction extends BaseBikeAction {

    private String eventTypeId;
    private String descriptiveName;
    private Date registerDate;
    private Long mileage;

    @SkipValidation
    @Action("new-bike-event")
    public String execute() {
        return SUCCESS;
    }

    @Action("register-bike-event")
    public String registerBikeEvent() {
        LOG.debug("Registering new bike event for bike {}", getBikeId());

        BikeEvent bikeEvent = BikeEvent.create(selectedBike, currentUser)
                .withEventTypeId(eventTypeId)
                .withDescriptiveName(descriptiveName)
                .withRegisterDate(registerDate)
                .withMileage(mileage)
                .build();

        LOG.debug("Storing new Bike Event {}", bikeEvent);
        bikeHistory.put(bikeEvent);

        LOG.debug("Returning to show bike {}", getBikeId());
        return TO_SHOW_BIKE;
    }

    public List<EventType> getEventTypesList() {
        return eventTypes.listAllowedEventTypes();
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

    @StringLengthFieldValidator(minLength = "4", key = "bikeEvent.descriptiveNameTooShort")
    public void setDescriptiveName(String descriptiveName) {
        this.descriptiveName = descriptiveName;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    @RequiredFieldValidator(key = "bikeEvent.registerDateIsRequired")
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Long getMileage() {
        return mileage;
    }

    @RequiredFieldValidator(key = "bikeEvent.mileageIsRequired")
    public void setMileage(Long mileage) {
        this.mileage = mileage;
    }

}
