package com.gruuf.web.actions.admin;

import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.services.BikeHistory;
import com.gruuf.services.Garage;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.struts2.convention.annotation.Result;

@Tokens(Token.ADMIN)
@Result(location = "/", type = "redirect")
public class ReindexAction extends BaseAction {

    private BikeHistory bikeHistory;
    private Garage garage;

    public String execute() {
        if (garage != null) {
            garage.reindex();
        }
        if (bikeHistory != null) {
            bikeHistory.reindex();
        }

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

}
