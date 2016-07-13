package com.gruuf.web.actions.bike;

import com.gruuf.services.Garage;
import com.gruuf.web.RbwActions;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.gruuf.model.Bike;
import com.gruuf.model.User;
import com.gruuf.web.interceptors.CurrentUserAware;
import com.gruuf.web.interceptors.GarageAware;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.interceptor.validation.SkipValidation;

public class Add extends ActionSupport implements GarageAware, CurrentUserAware {

    private Garage garage;
    private User currentUser;

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

    @Override
    public void setUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
