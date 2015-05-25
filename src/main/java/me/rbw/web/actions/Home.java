package me.rbw.web.actions;

import com.opensymphony.xwork2.ActionSupport;
import me.rbw.services.Garage;
import me.rbw.model.Motorbike;
import me.rbw.model.User;
import me.rbw.web.interceptors.GarageAware;
import me.rbw.web.interceptors.UserAware;

import java.util.List;

public class Home extends ActionSupport implements UserAware, GarageAware {

    private User currentUser;
    private Garage garage;

    public String execute() throws Exception {
        return SUCCESS;
    }

    public List<Motorbike> getMotorbikes() {
        return garage.getMotorbikes(currentUser);
    }

    public void setUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }
}
