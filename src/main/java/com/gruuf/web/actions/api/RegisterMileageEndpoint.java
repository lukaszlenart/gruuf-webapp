package com.gruuf.web.actions.api;

import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.gruuf.GruufConstants;
import com.gruuf.auth.Anonymous;
import com.gruuf.model.Bike;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.EventType;
import com.gruuf.model.User;
import com.gruuf.services.BikeHistory;
import com.gruuf.services.EventTypes;
import com.gruuf.services.Garage;
import com.gruuf.services.UserStore;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.StrutsConstants;
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
public class RegisterMileageEndpoint extends BaseEndpoint {

    private static final Logger LOG = LogManager.getLogger(RegisterMileageEndpoint.class);

    @Inject
    private Garage garage;
    @Inject
    private UserStore userStore;
    @Inject
    private EventTypes eventTypes;
    @Inject(GruufConstants.FACEBOOK_VERIFY_TOKEN)
    private String gruufVerifyToken;
    @Inject(StrutsConstants.STRUTS_DEVMODE)
    private String devMode;

    private UserProfileResponse response;

    private String verifyToken;
    private String asid;
    private RegisterMileagePayload payload;

    public String execute() {
        if (wrongToken()) {
            LOG.warn("Wrong token {}", verifyToken);
            response = UserProfileResponse.failed();
        } else {
            User user = userStore.findUniqueBy("facebookId", asid);
            if (user == null) {
                LOG.warn("No Facebook ID: {}", asid);
                response = UserProfileResponse.failed();
            } else {
                LOG.debug("Valid profile matched: {} = {}", asid, user.getEmail());
                UserProfile profile = new UserProfile(user);

                Bike bike = garage.get(payload.bikeId);
                if (bike == null ) {
                    LOG.warn("No Facebook ID: {}", asid);
                    response = UserProfileResponse.failed();
                } else {
                    BikeEvent bikeEvent = BikeEvent.create(bike, user)
                            .withMileage(payload.mileage)
                            .withMth(payload.mth)
                            .withEventTypeId(Collections.singleton(eventTypes.getMileageEventType().getId()))
                            .withDescription(getText("bike.systemMileageUpdate"))
                            .withRegisterDate(DateTime.now().withTimeAtStartOfDay().toDate())
                            .markAsTemporary()
                            .build();
                    history.put(bikeEvent);
                }

                List<Bike> bikes = garage.findByOwner(user);
                List<BikeProfile> bikeProfiles = bikes.stream().map(this::createBikeProfile).collect(Collectors.toList());

                response = UserProfileResponse.success(profile, bikeProfiles);
            }
        }

        return SUCCESS;
    }

    private boolean wrongToken() {
        return !Boolean.parseBoolean(devMode) && !gruufVerifyToken.equals(verifyToken);
    }

    public void setVerifyToken(String verifyToken) {
        this.verifyToken = verifyToken;
    }

    public void setAsid(String asid) {
        this.asid = asid;
    }

    public void setPayload(RegisterMileagePayload payload) {
        this.payload = payload;
    }

    public UserProfileResponse getResponse() {
        return response;
    }

    public static class RegisterMileagePayload {
        private String bikeId;
        private Long mth;
        private Long mileage;

        public String getBikeId() {
            return bikeId;
        }

        public void setBikeId(String bikeId) {
            this.bikeId = bikeId;
        }

        public Long getMth() {
            return mth;
        }

        public void setMth(Long mth) {
            this.mth = mth;
        }

        public Long getMileage() {
            return mileage;
        }

        public void setMileage(Long mileage) {
            this.mileage = mileage;
        }
    }
}
