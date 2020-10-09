package com.gruuf.web.actions.bike;

import com.gruuf.auth.Anonymous;
import com.gruuf.model.Bike;
import com.gruuf.model.BikeDetails;
import com.gruuf.model.BikeEventStatus;
import com.gruuf.model.SearchPeriod;
import com.gruuf.services.Garage;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;

import static com.opensymphony.xwork2.Action.INPUT;

@Anonymous()
@Result(name = INPUT, location = "bike/web-report")
public class WebReportAction extends BaseAction {

    @Inject
    private Garage garage;

    private String bikeId;
    private Bike selectedBike;
    private BikeDetails bikeDetails;

    @SkipValidation
    public String execute() {
        selectedBike = garage.get(bikeId);
        if (selectedBike != null) {
            currentUser = selectedBike.getOwner();
            bikeDetails = loadBikeDetails(selectedBike, SearchPeriod.ALL, BikeEventStatus.SYSTEM, BikeEventStatus.NEW);
        }

        return "web-report";
    }

    public BikeDetails getBikeDetails() {
        return bikeDetails;
    }

    public String getBikeId() {
        return bikeId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public Bike getSelectedBike() {
        return selectedBike;
    }
}
