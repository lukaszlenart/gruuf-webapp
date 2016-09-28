package com.gruuf.web.actions.bike;

import com.gruuf.model.Bike;
import com.gruuf.services.Garage;
import com.gruuf.web.GruufActions;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;

import static com.opensymphony.xwork2.Action.INPUT;

@Result(name = INPUT, location = "bike/add-input")
public class AddAction extends BaseBikeAction implements Validateable {

    @SkipValidation
    public String execute() {
        return INPUT;
    }

    @Action("add-submit")
    public String addSubmit() {
        Bike bike = Bike
                .create(currentUser)
                .withFriendlyName(friendlyName)
                .withVIN(vin)
                .build();

        garage.put(bike);
        return GruufActions.GARAGE;
    }

    public void validateAddSubmit() throws Exception {
        if (garage.findUniqueBy("vin", vin) != null) {
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
