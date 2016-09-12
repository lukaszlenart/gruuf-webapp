package com.gruuf.web.actions.bike;

import com.gruuf.model.Bike;
import com.gruuf.model.BikeDetails;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.EventType;
import com.gruuf.services.BikeHistory;
import com.gruuf.services.Garage;
import com.gruuf.web.RbwActions;
import com.gruuf.web.actions.BaseAction;
import com.gruuf.web.interceptors.BikeHistoryAware;
import com.gruuf.web.interceptors.GarageAware;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;

import java.util.Date;
import java.util.List;

@Result(name = "to-show-bike", location = "show", type = "redirectAction", params = {"bikeId", "${bikeId}"})
public class Show extends BaseAction implements GarageAware, BikeHistoryAware {

    private static Logger LOG = LogManager.getLogger(Show.class);

    private String bikeId;
    private BikeDetails bikeDetails;

    private Garage garage;
    private BikeHistory bikeHistory;

    private String eventTypeId;
    private String descriptiveName;
    private Date registerDate;
    private Long mileage;

    @SkipValidation
    public String execute() {
        Bike selectedBike = garage.getBike(bikeId);
        if (garage.canView(selectedBike, currentUser)) {
            LOG.debug("User {} can view bike {}", selectedBike, currentUser);

            List<BikeEvent> events = bikeHistory.get(selectedBike);
            LOG.debug("Found Bike Events for bike {}: {}", bikeId, events);

            bikeDetails = BikeDetails.create(selectedBike).withUser(currentUser).withHistory(events);

            return RbwActions.BIKE_SHOW;
        } else {
            LOG.warn("User {} cannot view bike {}", selectedBike, currentUser);
            return RbwActions.HOME;
        }
    }

    @Action("register-bike-event")
    public String registerBikeEvent() {
        LOG.debug("Registering new bike event for bike {}", bikeId);

        Bike selectedBike = garage.getBike(bikeId);

        BikeEvent bikeEvent = BikeEvent.create(selectedBike)
                .withEventTypeId(eventTypeId)
                .withDescriptiveName(descriptiveName)
                .withRegisterDate(registerDate)
                .withMileage(mileage)
                .build();

        LOG.debug("Storing new Bike Event {}", bikeEvent);
        bikeHistory.register(bikeEvent);

        LOG.debug("Returning to show bike {}", bikeId);
        return "to-show-bike";
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

    @Override
    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    @Override
    public void setBikeHistory(BikeHistory bikeHistory) {
        this.bikeHistory = bikeHistory;
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

    @StringLengthFieldValidator(minLength = "12")
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
}
