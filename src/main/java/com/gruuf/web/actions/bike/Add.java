package com.gruuf.web.actions.bike;

import com.gruuf.model.Bike;
import com.gruuf.services.Garage;
import com.gruuf.web.RbwActions;
import com.gruuf.web.actions.BaseAction;
import com.gruuf.web.interceptors.GarageAware;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.interceptor.validation.SkipValidation;

public class Add extends BaseAction implements GarageAware {

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
        return RbwActions.HOME;
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
