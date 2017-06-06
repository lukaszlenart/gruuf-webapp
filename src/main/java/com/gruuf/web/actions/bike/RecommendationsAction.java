package com.gruuf.web.actions.bike;

import com.gruuf.auth.BikeRestriction;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.BikeRecommendation;
import com.gruuf.model.RecommendationDescriptor;
import com.gruuf.services.BikeHistory;
import com.gruuf.services.Recommendations;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.List;

@BikeRestriction
public class RecommendationsAction extends BaseBikeAction {

    private static final Logger LOG = LogManager.getLogger(RecommendationsAction.class);

    private Recommendations recommendations;
    private BikeHistory history;

    public String execute() throws Exception {
        return SUCCESS;
    }

    @Inject
    public void setRecommendations(Recommendations recommendations) {
        this.recommendations = recommendations;
    }

    @Inject
    public void setHistory(BikeHistory history) {
        this.history = history;
    }

    public List<RecommendationDescriptor> getBikeRecommendations() {
        List<BikeRecommendation> recommendations = this.recommendations.listApprovedByBikeMetadata(currentUser, selectedBike.getBikeMetadata());
        List<BikeEvent> bikeEvents = history.listByBike(selectedBike);

        LOG.debug("Recommendations: {}", recommendations);
        LOG.debug("Bike events: {}", bikeEvents);

        List<RecommendationDescriptor> result = new ArrayList<>();
        for (BikeRecommendation recommendation : recommendations) {
            boolean missingRecommendation = true;
            for (BikeEvent bikeEvent : bikeEvents) {
                if (bikeEvent.getEventTypes().contains(recommendation.getEventType())) {
                    if (matchesPeriod(bikeEvent, recommendation, bikeEvents)) {
                        RecommendationDescriptor descriptor = new RecommendationDescriptor(recommendation, bikeEvent);
                        result.add(descriptor);
                        missingRecommendation = false;
                        break;
                    }
                }
            }

            if (missingRecommendation) {
                result.add(new RecommendationDescriptor(recommendation, null));
            }
        }

        return result;
    }

    private boolean matchesPeriod(BikeEvent bikeEvent, BikeRecommendation recommendation, List<BikeEvent> bikeEvents) {
        boolean result = false;

        if (recommendation.isMileagePeriod()) {
            for (BikeEvent event : bikeEvents) {
                if (!event.getId().equals(bikeEvent.getId()) && bikeEvent.isMileage() && event.isMileage()) {
                    result = (bikeEvent.getMileage() - event.getMileage()) <= recommendation.getMileagePeriod();
                    if (result) {
                        break;
                    }
                }
            }
        }

        if (recommendation.isMonthPeriod()) {
            for (BikeEvent event : bikeEvents) {
                if (!event.getId().equals(bikeEvent.getId())) {

                    LocalDate from = new DateTime(bikeEvent.getRegisterDate()).toLocalDate();
                    LocalDate to = new DateTime(event.getRegisterDate()).toLocalDate();

                    result = Period.fieldDifference(from, to).getMonths() < recommendation.getMonthPeriod();
                    if (result) {
                        break;
                    }
                }
            }
        }

        if (recommendation.isMthPeriod()) {
            for (BikeEvent event : bikeEvents) {
                if (!event.getId().equals(bikeEvent.getId()) && bikeEvent.isMth() && event.isMth()) {
                    result = (bikeEvent.getMth() - event.getMth()) <= recommendation.getMthPeriod();
                    if (result) {
                        break;
                    }
                }
            }
        }

        return result;
    }

}
