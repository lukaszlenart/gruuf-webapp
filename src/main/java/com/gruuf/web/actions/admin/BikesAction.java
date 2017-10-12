package com.gruuf.web.actions.admin;

import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.model.Bike;
import com.gruuf.services.Garage;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;

import java.util.List;

@Tokens(Token.ADMIN)
public class BikesAction extends BaseAction {

    private Garage garage;

    public String execute() {
        return "bikes";
    }

    public List<Bike> getList() {
        return garage.list();
    }

    @Inject
    public void setGarage(Garage garage) {
        this.garage = garage;
    }
}
