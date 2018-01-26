package com.gruuf.web.actions.api;

import com.gruuf.GruufConstants;
import com.gruuf.auth.Anonymous;
import com.gruuf.model.Bike;
import com.gruuf.model.User;
import com.gruuf.services.BikeHistory;
import com.gruuf.services.Garage;
import com.gruuf.services.UserStore;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.gruuf.web.actions.BaseAction.JSON;
import static com.opensymphony.xwork2.Action.SUCCESS;

@Result(name = SUCCESS, type = JSON, params = {"root", "response"})
@InterceptorRefs({
        @InterceptorRef("gruufDefaultDev"),
        @InterceptorRef("json")
})
@Anonymous
public class UserProfileEndpoint extends BaseAction {

    private static final Logger LOG = LogManager.getLogger(UserProfileEndpoint.class);

    @Inject
    private Garage garage;
    @Inject
    private BikeHistory history;
    @Inject
    private UserStore userStore;
    @Inject(GruufConstants.FACEBOOK_VERIFY_TOKEN)
    private String gruufVerifyToken;

    private UserProfileResponse response;
    private String verifyToken;
    private String asid;

    public String execute() {
        if (verifyToken()) {
            User user = userStore.findUniqueBy("facebookId", asid);
            if (user == null) {
                LOG.warn("No Facebook ID: {}", asid);
                response = UserProfileResponse.failed();
            } else {
                LOG.debug("Valid profile matched: {} = {}", asid, user.getEmail());
                UserProfile profile = new UserProfile(user);

                List<Bike> bikes = garage.findByOwner(user);
                List<BikeProfile> bikeProfiles = bikes.stream().map(this::createBikeProfile).collect(Collectors.toList());

                response = UserProfileResponse.ok(profile, bikeProfiles);
            }
        } else {
            LOG.warn("Wrong token {}", verifyToken);
            response = UserProfileResponse.failed();
        }

        return SUCCESS;
    }

    private BikeProfile createBikeProfile(Bike bike) {
        // List<BikeEvent> events = history.listRecentByBike(bike);

        Long mileage = null;
        if (bike.isShowMileage()) {
            mileage = history.findCurrentMileage(bike);
        }

        Long mth = null;
        if (bike.isShowMth()) {
            mth = history.findCurrentMth(bike);
        }

        return new BikeProfile(bike, mileage, mth);
    }

    private boolean verifyToken() {
        return gruufVerifyToken.equals(verifyToken);
    }

    public void setVerifyToken(String verifyToken) {
        this.verifyToken = verifyToken;
    }

    public void setAsid(String asid) {
        this.asid = asid;
    }

    public UserProfileResponse getResponse() {
        return response;
    }

    public static class UserProfileResponse {
        private final UserProfile profile;
        private final List<BikeProfile> bikes;

        private String status;

        private UserProfileResponse() {
            profile = null;
            bikes = Collections.emptyList();
        }

        private UserProfileResponse(UserProfile profile, List<BikeProfile> bikes) {
            this.profile = profile;
            this.bikes = bikes;
        }

        public UserProfile getProfile() {
            return profile;
        }

        public List<BikeProfile> getBikes() {
            return bikes;
        }

        public String getStatus() {
            return status;
        }

        public static UserProfileResponse ok(UserProfile profile, List<BikeProfile> bikes) {
            UserProfileResponse result = new UserProfileResponse(profile, bikes);
            result.status = "success";
            return result;
        }

        public static UserProfileResponse failed() {
            UserProfileResponse result = new UserProfileResponse();
            result.status = "failed";
            return result;
        }
    }

}
