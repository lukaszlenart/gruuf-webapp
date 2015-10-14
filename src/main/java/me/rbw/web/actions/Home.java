package me.rbw.web.actions;

import com.opensymphony.xwork2.ActionSupport;
import me.rbw.services.Garage;
import me.rbw.model.Bike;
import me.rbw.model.User;
import me.rbw.web.interceptors.GarageAware;
import me.rbw.web.interceptors.CurrentUserAware;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Home extends ActionSupport implements CurrentUserAware, GarageAware {

    private static Logger LOG = LogManager.getLogger(Home.class);

    private User currentUser;
    private Garage garage;

    public String execute() throws Exception {
        return SUCCESS;
    }

    public List<Bike> getBikes() {
        List<Bike> bikes = garage.get(currentUser.getId());

        LOG.debug("Found following bikes {} for user {}", bikes, currentUser);

        return bikes;
    }

    public void setUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }
}
