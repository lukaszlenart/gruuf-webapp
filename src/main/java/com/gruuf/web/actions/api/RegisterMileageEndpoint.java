package com.gruuf.web.actions.api;

import com.gruuf.GruufConstants;
import com.gruuf.auth.Bot;
import com.gruuf.model.Bike;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.User;
import com.gruuf.services.EventTypes;
import com.gruuf.services.Garage;
import com.gruuf.services.UserStore;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.StrutsConstants;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.joda.time.DateTime;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.gruuf.web.actions.BaseAction.JSON;
import static com.opensymphony.xwork2.Action.SUCCESS;

@Result(name = SUCCESS, type = JSON, params = {"root", "response"})
@InterceptorRefs({
        @InterceptorRef("gruufDefault"),
        @InterceptorRef("json")
})
@Bot
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
                if (bike == null) {
                    LOG.warn("No Facebook ID: {}", asid);
                    response = UserProfileResponse.failed();
                } else {
                    BikeEvent.BikeEventBuilder bikeEvent = BikeEvent.create(bike, user)
                            .withRegisterDate(DateTime.now().withTimeAtStartOfDay().toDate())
                            .markAsTemporary();

                    if (payload.isMth()) {
                        bikeEvent = bikeEvent
                            .withDescription(getText("bike.systemMthUpdate"))
                            .withEventTypeId(Collections.singleton(eventTypes.getMthEventType().getId()))
                            .withMth(payload.mileage);
                    } else {
                        bikeEvent = bikeEvent
                            .withDescription(getText("bike.systemMileageUpdate"))
                            .withEventTypeId(Collections.singleton(eventTypes.getMileageEventType().getId()))
                            .withMileage(payload.mileage);
                    }

                    history.put(bikeEvent.build());
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
        private Long mileage;
        private String mileageType;

        public void setBikeId(String bikeId) {
            this.bikeId = bikeId;
        }

        public void setMileage(Long mileage) {
            this.mileage = mileage;
        }

        public void setMileageType(String mileageType) {
            this.mileageType = mileageType;
        }

        public boolean isMth() {
            return "mth".equalsIgnoreCase(mileageType);
        }
    }
}
