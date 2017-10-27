package com.gruuf.web.actions.tasks;

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
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.joda.time.DateTime;

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

    private static final Logger LOG = LogManager.getLogger(DailyRecommendationCheckTask.class);

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
            LOG.warn("Select bike is null!");
            return ERROR;
        }

        LOG.info("Performing daily recommendation check for {}", selectedBike);

        List<BikeRecommendation> missingRecommendations = listMissingRecommendations();

        if (missingRecommendations.size() > 0) {
            LOG.info("Found missing recommendations");

            User owner = selectedBike.getOwner();

            // FIXME: implement LocaleProvider instead once fixed in Struts
            ActionContext.getContext().setLocale(owner.getUserLocale().toLocale());

            String subject = getText("recommendations.missingRecommendations", new String[]{selectedBike.getName()});
            StringBuilder body = new StringBuilder("## ")
                    .append(getText("recommendations.followingRecommendationsAreGoingToExpire"))
                    .append(":\n\n");

            for (BikeRecommendation recommendation : missingRecommendations) {
                if (recommendation.isNotify()) {
                    String eventName = recommendation.getEventType().getNames().get(owner.getUserLocale());

                    body.append("### ").append(eventName).append(":\n\n");
                    if (recommendation.getDescription().getContent().trim().startsWith("-")) {
                        body.append("  ").append(recommendation.getDescription().getContent()).append("\n\n");
                    } else {
                        body.append("  - ").append(recommendation.getDescription().getContent()).append("\n\n");
                    }
                }
            }

            mailBox.notifyOwner(owner.getEmail(), owner.getFullName(), subject, body.toString());
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

        if (recommendation.isMonthPeriod()) {
            for (BikeEvent event : bikeEvents) {
                if (!event.getId().equals(bikeEvent.getId()) && event.getEventTypes().contains(recommendation.getEventType())) {

                    DateTime latestEvent = new DateTime(event.getRegisterDate()).plusDays(DAYS_CHECK);

                    result = latestEvent.isBeforeNow();

                    LOG.info("Month period check: {} for data: bike event date={}, event date={}, recommendation date={}",
                            result, bikeEvent.getRegisterDate(), event.getRegisterDate(), recommendation.getMonthPeriod());

                    if (result) {
                        break;
                    }
                }
            }
        }

        if (recommendation.isMileagePeriod() && bikeEvent.isMileage()) {
            for (BikeEvent event : bikeEvents) {
                if (!event.getId().equals(bikeEvent.getId()) && event.isMileage() && event.getEventTypes().contains(recommendation.getEventType())) {
                    result = (history.findCurrentMileage(selectedBike) - bikeEvent.getMileage() + MILEAGE_CHECK) <= recommendation.getMileagePeriod();

                    LOG.info("Mileage period check: {} for data: bike mileage={}, event mileage={}, recommendation mileage={}",
                            result, bikeEvent.getMileage(), event.getMileage(), recommendation.getMileagePeriod());

                    if (result) {
                        break;
                    }
                }
            }
        }

        if (recommendation.isMthPeriod() && bikeEvent.isMth()) {
            for (BikeEvent event : bikeEvents) {
                if (!event.getId().equals(bikeEvent.getId()) && event.isMth() && event.getEventTypes().contains(recommendation.getEventType())) {
                    result = (history.findCurrentMth(selectedBike) - bikeEvent.getMth() + MTH_CHECK) <= recommendation.getMthPeriod();

                    LOG.info("Mth period check: {} for data: bike mth={}, event mth={}, recommendation mth={}",
                            result, bikeEvent.getMth(), event.getMth(), recommendation.getMthPeriod());

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
        } else {
            LOG.error("bikeId cannot be null!");
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
