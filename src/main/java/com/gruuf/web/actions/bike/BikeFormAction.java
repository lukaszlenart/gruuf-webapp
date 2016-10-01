package com.gruuf.web.actions.bike;

import com.gruuf.auth.BikeRestriction;
import com.gruuf.model.Bike;
import com.gruuf.services.Garage;
import com.gruuf.web.GruufActions;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;

import static com.opensymphony.xwork2.Action.INPUT;

@Result(name = INPUT, location = "bike/bike-form")
@BikeRestriction
public class BikeFormAction extends BaseBikeAction implements Validateable {

    private static final Logger LOG = LogManager.getLogger(BikeFormAction.class);

    @SkipValidation
    public String execute() {
        if (selectedBike != null) {
            friendlyName = selectedBike.getName();
            vin = selectedBike.getVin();
        }
        return INPUT;
    }

    @Action("bike-form-submit")
    public String bikeFormSubmit() {
        if (selectedBike == null) {
            LOG.debug("Creating new bike with name {} and vin {}", friendlyName, vin);
            Bike bike = Bike
                    .create(currentUser)
                    .withFriendlyName(friendlyName)
                    .withVIN(vin)
                    .build();

            garage.put(bike);
        } else {
            LOG.debug("Updating existing bike {}", selectedBike.getId());
            Bike bike = Bike.clone(selectedBike)
                    .withFriendlyName(friendlyName)
                    .withVIN(vin)
                    .build();

            garage.put(bike);
        }
        return GruufActions.GARAGE;
    }

    public void validateBikeFormSubmit() throws Exception {
        Bike existingBike = garage.findUniqueBy("vin", vin);

        if (selectedBike == null && existingBike != null) {
            LOG.debug("Creating new bike with VIN {} that was already registered as {}", vin, existingBike);
            addFieldError("vin", getText("bike.vinAlreadyUsed"));
        }

        if (selectedBike != null && existingBike != null
                && !selectedBike.getId().equals(existingBike.getId())) {

            LOG.debug("Updating existing bike {} with new VIN {} that was already registered {}",
                    existingBike, vin, selectedBike);
            addFieldError("vin", getText("bike.vinAlreadyUsed"));
        }
    }

    private String friendlyName;
    private String vin;

    public String getFriendlyName() {
        return friendlyName;
    }

    @RequiredStringValidator(key = "bike.friendlyNameIsRequired")
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getVin() {
        return vin;
    }

    @RequiredStringValidator(key = "bike.vinIsRequired")
    public void setVin(String vin) {
        this.vin = vin;
    }

    @Inject
    public void setGarage(Garage garage) {
        this.garage = garage;
    }

}
