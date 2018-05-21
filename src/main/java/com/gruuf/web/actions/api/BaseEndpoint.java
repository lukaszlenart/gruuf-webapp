package com.gruuf.web.actions.api;

import com.gruuf.model.Bike;
import com.gruuf.services.BikeHistory;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;

import java.util.Collections;
import java.util.List;

abstract class BaseEndpoint extends BaseAction {

    @Inject
    protected BikeHistory history;

    protected BikeProfile createBikeProfile(Bike bike) {
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

    public static class UserProfileResponse {

        private String status;
        private UserProfile profile = null;
        private List<BikeProfile> bikes = Collections.emptyList();

        public String getStatus() {
            return status;
        }

        public UserProfile getProfile() {
            return profile;
        }

        public List<BikeProfile> getBikes() {
            return bikes;
        }

        public static UserProfileResponse failed() {
            UserProfileResponse response = new UserProfileResponse();
            response.status = "failed";
            return response;
        }

        public static UserProfileResponse success(UserProfile profile, List<BikeProfile> bikeProfiles) {
            UserProfileResponse response = new UserProfileResponse();
            response.status = "success";
            response.profile = profile;
            response.bikes = bikeProfiles;
            return response;
        }
    }

}
