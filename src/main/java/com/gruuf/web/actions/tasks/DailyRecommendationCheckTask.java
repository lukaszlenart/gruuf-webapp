package com.gruuf.web.actions.tasks;

import com.github.rjeschke.txtmark.Processor;
import com.gruuf.auth.Anonymous;
import com.gruuf.model.Bike;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.BikeRecommendation;
import com.gruuf.model.User;
import com.gruuf.services.BikeHistory;
import com.gruuf.services.Garage;
import com.gruuf.services.MailBox;
import com.gruuf.services.Recommendations;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.List;

import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;

@Anonymous
@Results({
        @Result(name = SUCCESS, type = "httpheader", params = {"status", "200"}),
        @Result(name = ERROR, type = "httpheader", params = {"status", "401"})
})
public class DailyRecommendationCheckTask extends BaseAction {

    private static final Integer MILEAGE_CHECK = 1000;
    private static final int DAYS_CHECK = 14;
    private static final Integer MTH_CHECK = 10;

    private Bike selectedBike;

    private Garage garage;
    private Recommendations recommendations;
    private BikeHistory history;
    private MailBox mailBox;

    public String execute() {
        if (selectedBike == null) {
            return ERROR;
        }

        List<BikeRecommendation> missingRecommendations = listMissingRecommendations();

        if (missingRecommendations.size() > 0) {
            User owner = selectedBike.getOwner();

            String subject = getText("recommendations.missingRecommendations");
            StringBuilder message = new StringBuilder(getText("recommendations.followingRecommendationsAreGoingToExpire") + ":\n\n");

            for (BikeRecommendation recommendation : missingRecommendations) {
                String eventName = recommendation.getEventType().getNames().get(owner.getUserLocale());
                String content = Processor.process(recommendation.getDescription().getContent());

                message.append(eventName).append(":\n").append(content).append("\n\n");
            }

            mailBox.notifyOwner(owner.getEmail(), owner.getFullName(), subject, message.toString());
        }

        return SUCCESS;
    }

    private List<BikeRecommendation> listMissingRecommendations() {

        List<BikeRecommendation> recommendations = this.recommendations.listFor(selectedBike.getBikeMetadata());
        List<BikeEvent> bikeEvents = history.listByBike(selectedBike);

        List<BikeRecommendation> missingRecommendations = new ArrayList<>();
        for (BikeRecommendation recommendation : recommendations) {
            boolean missingRecommendation = true;
            for (BikeEvent bikeEvent : bikeEvents) {
                if (bikeEvent.getEventTypes().contains(recommendation.getEventType())) {
                    if (matchesPeriod(bikeEvent, recommendation, bikeEvents)) {
                        missingRecommendation = false;
                        break;
                    }
                }
            }

            if (missingRecommendation) {
                missingRecommendations.add(recommendation);
            }
        }

        return missingRecommendations;
    }

    private boolean matchesPeriod(BikeEvent bikeEvent, BikeRecommendation recommendation, List<BikeEvent> bikeEvents) {
        boolean result = false;

        if (recommendation.isMileagePeriod()) {
            for (BikeEvent event : bikeEvents) {
                if (!event.getId().equals(bikeEvent.getId()) && bikeEvent.isMileage() && event.isMileage()) {
                    result = (bikeEvent.getMileage() - event.getMileage()) <= recommendation.getMileagePeriod() - MILEAGE_CHECK;
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
                    LocalDate to = new DateTime(event.getRegisterDate()).toLocalDate().plusDays(DAYS_CHECK);

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
                    result = (bikeEvent.getMth() - event.getMth()) <= recommendation.getMthPeriod() - MTH_CHECK;
                    if (result) {
                        break;
                    }
                }
            }
        }

        return result;
    }

    public void setBikeId(String bikeId) {
        if (StringUtils.isNotEmpty(bikeId)) {
            selectedBike = garage.get(bikeId);
        }
    }

    @Inject
    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    @Inject
    public void setRecommendations(Recommendations recommendations) {
        this.recommendations = recommendations;
    }

    @Inject
    public void setHistory(BikeHistory history) {
        this.history = history;
    }

    @Inject
    public void setMailBox(MailBox mailBox) {
        this.mailBox = mailBox;
    }
}
