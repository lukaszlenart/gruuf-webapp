package com.gruuf.web.actions.bike;

import com.gruuf.model.Bike;
import com.gruuf.model.BikeDescriptor;
import com.gruuf.services.Garage;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;

import static com.opensymphony.xwork2.Action.INPUT;

@Result(name = INPUT, type = "tiles", location = "bike/vin-check-input")
public class VinCheckAction extends BaseAction {

    private String vin;

    private Garage garage;
    private BikeDescriptor bike;

    @SkipValidation
    public String execute() {
        return INPUT;
    }

    @Action("vin-check-search")
    public String search() {
        Bike b = garage.findUniqueBy("vin", vin);
        if (b == null) {
            addActionMessage(getText("bike.noBikeWithGivenVin"));
        } else {
            bike = new BikeDescriptor(b);
        }
        return INPUT;
    }

    @RequiredStringValidator()
    @StringLengthFieldValidator(minLength = "6")
    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getVin() {
        return vin;
    }

    public BikeDescriptor getBike() {
        return bike;
    }

    @Inject
    public void setGarage(Garage garage) {
        this.garage = garage;
    }
}
