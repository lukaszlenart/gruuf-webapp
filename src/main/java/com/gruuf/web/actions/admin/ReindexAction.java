package com.gruuf.web.actions.admin;

import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.services.BikeHistory;
import com.gruuf.services.EventTypes;
import com.gruuf.services.Garage;
import com.gruuf.services.Recommendations;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.struts2.convention.annotation.Result;

@Tokens(Token.ADMIN)
@Result(location = "/", type = "redirect")
public class ReindexAction extends BaseAction {

    private BikeHistory bikeHistory;
    private Garage garage;
    private EventTypes eventTypes;
    private Recommendations recommendations;

    public String execute() {
        if (garage != null) {
            garage.reindex();
        }
        if (bikeHistory != null) {
            bikeHistory.reindex();
        }
        if (eventTypes != null) {
            eventTypes.reindex();
        }
        recommendations.reindex();

        return SUCCESS;
    }


    @Inject
    public void setBikeHistory(BikeHistory bikeHistory) {
        this.bikeHistory = bikeHistory;
    }

    @Inject
    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    @Inject
    public void setEventTypes(EventTypes eventTypes) {
        this.eventTypes = eventTypes;
    }

    @Inject
    public void setRecommendations(Recommendations recommendations) {
        this.recommendations = recommendations;
    }
}
