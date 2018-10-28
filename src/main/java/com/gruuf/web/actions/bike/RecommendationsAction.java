package com.gruuf.web.actions.bike;

import com.gruuf.auth.BikeRestriction;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.BikeEventStatus;
import com.gruuf.model.BikeRecommendation;
import com.gruuf.model.RecommendationDescriptor;
import com.gruuf.services.BikeHistory;
import com.gruuf.services.Recommendations;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@BikeRestriction
public class RecommendationsAction extends BaseBikeAction {

    private static final Logger LOG = LogManager.getLogger(RecommendationsAction.class);

    @Inject
    private Recommendations recommendations;
    @Inject 
    private BikeHistory history;

    public String execute() {
        return SUCCESS;
    }

    public List<RecommendationDescriptor> getBikeRecommendations() {
        List<BikeRecommendation> recommendations = this.recommendations.listApprovedByBike(currentUser, selectedBike);
        List<BikeEvent> bikeEvents = history.listByBike(selectedBike, BikeEventStatus.NEW);

        LOG.debug("Recommendations: {}", recommendations);
        LOG.debug("Bike events: {}", bikeEvents);

        List<RecommendationDescriptor> result = new ArrayList<>();
        for (BikeRecommendation recommendation : recommendations) {
            boolean missingRecommendation = true;
            for (BikeEvent bikeEvent : bikeEvents) {
                if (bikeEvent.getEventTypes().contains(recommendation.getEventType())) {
                    if (Recommendations.matchesPeriod(bikeEvent, recommendation, bikeEvents)) {
                        RecommendationDescriptor descriptor = new RecommendationDescriptor(currentUser.getUserLocale(), recommendation, bikeEvent);
                        result.add(descriptor);
                        missingRecommendation = false;
                        break;
                    }
                }
            }

            if (missingRecommendation) {
                result.add(new RecommendationDescriptor(currentUser.getUserLocale(), recommendation, null));
            }
        }

        return result;
    }

}
