package com.gruuf.web.actions.bike;

import com.gruuf.auth.BikeRestriction;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.EventType;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.util.TextParseUtil;
import com.opensymphony.xwork2.validator.annotations.LongRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;

import java.util.Date;
import java.util.List;

import static com.opensymphony.xwork2.Action.INPUT;

@Result(name = INPUT, location = "bike/new-bike-event")
@BikeRestriction
public class BikeEventAction extends BaseBikeAction implements Validateable {

    private static final Logger LOG = LogManager.getLogger(BikeEventAction.class);

    private String bikeEventId;

    private String eventTypeIds;
    private String descriptiveName;
    private Date registerDate;
    private Long mileage;
    private Long currentMileage;

    @SkipValidation
    @Action("new-bike-event")
    public String execute() {
        currentMileage = bikeHistory.findCurrentMileage(selectedBike);
        return INPUT;
    }

    @SkipValidation
    @Action("edit-bike-event")
    public String edit() {
        if (StringUtils.isEmpty(bikeEventId)) {
            LOG.debug("New bike event");
        } else {
            BikeEvent bikeEvent = bikeHistory.get(bikeEventId);
            if (bikeEvent.isEditable()) {
                eventTypeIds = StringUtils.join(bikeEvent.getEventTypeIds(), ",");
                descriptiveName = bikeEvent.getDescriptiveName();
                registerDate = bikeEvent.getRegisterDate();
                mileage = bikeEvent.getMileage();
            } else {
                LOG.debug("Bike event [{}] is not editable!", bikeEventId);
            }
        }

        currentMileage = bikeHistory.findCurrentMileage(selectedBike);
        return INPUT;
    }

    @Action("register-bike-event")
    public String registerBikeEvent() {
        if (StringUtils.isEmpty(bikeEventId)) {
            LOG.debug("Registering new bike event for bike {}", getBikeId());

            BikeEvent bikeEvent = BikeEvent.create(selectedBike, currentUser)
                    .withEventTypeId(TextParseUtil.commaDelimitedStringToSet(eventTypeIds))
                    .withDescriptiveName(descriptiveName)
                    .withRegisterDate(registerDate)
                    .withMileage(mileage)
                    .build();

            LOG.debug("Storing new Bike Event {}", bikeEvent);
            bikeHistory.put(bikeEvent);
        } else {
            BikeEvent oldBikeEvent = bikeHistory.get(bikeEventId);
            if (oldBikeEvent.isEditable()) {
                BikeEvent bikeEvent = BikeEvent.create(oldBikeEvent)
                        .withEventTypeId(TextParseUtil.commaDelimitedStringToSet(eventTypeIds))
                        .withDescriptiveName(descriptiveName)
                        .withRegisterDate(registerDate)
                        .withMileage(mileage)
                        .build();

                LOG.debug("Storing new Bike Event {}", bikeEvent);
                bikeHistory.put(bikeEvent);
            } else {
                LOG.debug("Bike event [{}] is not editable!", bikeEventId);
            }
        }

        LOG.debug("Returning to show bike {}", getBikeId());
        return TO_SHOW_BIKE;
    }

    public List<EventType> getEventTypesList() {
        return eventTypes.listAllowedEventTypes();
    }

    public String getEventTypeIds() {
        return eventTypeIds;
    }

    @StringLengthFieldValidator(minLength = "4", key = "bikeEvent.eventTypeIsRequired")
    public void setEventTypeIds(String eventTypeIds) {
        this.eventTypeIds = eventTypeIds;
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
    @LongRangeFieldValidator(min = "0", key = "bike.providedMileageMustBeZeroOrGreater")
    public void setMileage(Long mileage) {
        this.mileage = mileage;
    }

    public Long getCurrentMileage() {
        return currentMileage;
    }

    public void setCurrentMileage(Long currentMileage) {
        this.currentMileage = currentMileage;
    }

    public String getCurrentMileageHelp() {
        if (currentMileage == null) {
            return getText("bike.currentMileageNotDefined");
        }
        return getText("bike.currentMileageInKmIs");
    }

    public String getBikeEventId() {
        return bikeEventId;
    }

    public void setBikeEventId(String bikeEventId) {
        this.bikeEventId = bikeEventId;
    }
}
