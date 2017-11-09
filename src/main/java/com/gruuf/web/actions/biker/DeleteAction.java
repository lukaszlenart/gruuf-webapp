package com.gruuf.web.actions.biker;

import com.gruuf.services.Garage;
import com.gruuf.web.GruufAuth;
import com.gruuf.web.actions.BaseLoginAction;
import com.gruuf.web.actions.GarageAction;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.struts2.convention.annotation.Result;

import static com.opensymphony.xwork2.Action.INPUT;

@Result(name = INPUT, location = "biker/profile")
public class DeleteAction extends BaseLoginAction {

    private Garage garage;

    public String execute() {
        if (garage.findByOwner(currentUser).size() > 0) {
            addActionError(getText("user.youHaveStillBikesAssigned"));
            return INPUT;
        } else {
            userStore.drop(currentUser.getId());
            session.put(GruufAuth.AUTH_TOKEN, null);
            return GarageAction.LOGIN;
        }
    }

    @Inject
    public void setGarage(Garage garage) {
        this.garage = garage;
    }
}
