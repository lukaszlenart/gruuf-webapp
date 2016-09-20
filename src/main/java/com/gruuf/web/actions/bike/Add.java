package com.gruuf.web.actions.bike;

import com.gruuf.model.Bike;
import com.gruuf.services.Garage;
import com.gruuf.web.GruufActions;
import com.gruuf.web.actions.BaseAction;
import com.gruuf.web.interceptors.GarageAware;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;

import static com.opensymphony.xwork2.Action.INPUT;

@Result(name = INPUT, location = "bike/add-input")
public class Add extends BaseAction implements GarageAware, Validateable {

    private Garage garage;

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
        if (garage.findByVin(vin) != null) {
            addFieldError("vin", getText("bike.vinAlreadyUsed"));
        }
    }

    private String friendlyName;
    private String vin;

    public String getFriendlyName() {
        return friendlyName;
    }

    @RequiredStringValidator(message = "'Friendly name' is required!")
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getVin() {
        return vin;
    }

    @RequiredStringValidator(message = "'VIN' is required!")
    public void setVin(String vin) {
        this.vin = vin;
    }

    @Override
    public void setGarage(Garage garage) {
        this.garage = garage;
    }

}
