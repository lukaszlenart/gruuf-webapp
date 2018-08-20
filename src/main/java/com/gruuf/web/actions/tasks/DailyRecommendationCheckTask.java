package com.gruuf.web.actions.tasks;

import com.gruuf.auth.Anonymous;
import com.gruuf.model.Bike;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.BikeRecommendation;
import com.gruuf.model.BikeStatus;
import com.gruuf.services.BikeHistory;
import com.gruuf.services.Garage;
import com.gruuf.services.MailBox;
import com.gruuf.services.Recommendations;
import com.gruuf.services.UserStore;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;

@Anonymous
@Results({
    @Result(name = SUCCESS, type = "httpheader", params = {
        "status", "201",
        "headers.Cache-Control", "no-cache, no-store, must-revalidate",
        "headers.Pragma", "no-cache",
        "headers.Expires", "0"
    }),
    @Result(name = ERROR, type = "httpheader", params = {"status", "401"})
})
public class DailyRecommendationCheckTask extends BaseAction {

    private static final Logger LOG = LogManager.getLogger(DailyRecommendationCheckTask.class);

    private static final Integer MILEAGE_CHECK = 1000;
    private static final int DAYS_CHECK = 14;
    private static final Integer MTH_CHECK = 10;

    @Inject
    private UserStore userStore;
    @Inject
    private Garage garage;
    @Inject
    private Recommendations recommendations;
    @Inject
    private BikeHistory history;
    @Inject
    private MailBox mailBox;

    public String execute() {
        if (currentUser == null) {
            LOG.warn("User is null!");
            return ERROR;
        }

        LOG.info("Performing daily recommendation check for {}", currentUser.getFullName());

        StringBuilder body = null;

        for (Bike bike : listBikes()) {
            List<BikeRecommendation> missingRecommendations = listMissingRecommendations(bike);

            if (missingRecommendations.size() > 0) {
                LOG.info("Found missing recommendations");
                if (body == null) {
                    body = new StringBuilder("# ");
                }

                // FIXME: implement LocaleProvider instead, once fixed in Struts
                ActionContext.getContext().setLocale(currentUser.getUserLocale().toLocale());

                body.append(getText("recommendations.followingRecommendationsAreGoingToExpire")).append("\n\n")
                    .append("## ").append(bike.getName()).append(":\n\n");

                for (BikeRecommendation recommendation : missingRecommendations) {
                    if (recommendation.isNotify()) {
                        String eventName = recommendation.getEventType().getNames().get(currentUser.getUserLocale());

                        body.append("### ").append(eventName).append(":\n\n");
                        if (recommendation.getDescription().getContent().trim().startsWith("-")) {
                            body.append("  ").append(recommendation.getDescription().getContent()).append("\n\n");
                        } else {
                            body.append("  - ").append(recommendation.getDescription().getContent()).append("\n\n");
                        }
                    }
                }

            }
        }

        if (body != null) {
            String subject = getText("recommendations.missingRecommendations");
            mailBox.notifyOwner(currentUser.getEmail(), currentUser.getFullName(), subject, body.toString());
        }

        return SUCCESS;
    }

    private List<Bike> listBikes() {
        return garage
            .findByOwner(currentUser)
            .stream()
            .filter(bike -> bike.getStatus() == BikeStatus.NORMAL)
            .collect(Collectors.toList());
    }

    private List<BikeRecommendation> listMissingRecommendations(Bike bike) {

        List<BikeRecommendation> recommendations = this.recommendations.listFor(bike.getBikeMetadata());
        List<BikeEvent> bikeEvents = history.listByBike(bike);

        List<BikeRecommendation> missingRecommendations = new ArrayList<>();
        for (BikeRecommendation recommendation : recommendations) {
            boolean missingRecommendation = true;
            for (BikeEvent bikeEvent : bikeEvents) {
                if (bikeEvent.getEventTypes().contains(recommendation.getEventType())) {
                    if (matchesPeriod(bike, bikeEvent, recommendation, bikeEvents)) {
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

    private boolean matchesPeriod(Bike bike, BikeEvent bikeEvent, BikeRecommendation recommendation, List<BikeEvent> bikeEvents) {
        boolean result = false;

        if (recommendation.isMonthPeriod()) {
            for (BikeEvent event : bikeEvents) {
                if (event.getEventTypes().contains(recommendation.getEventType())) {

                    DateTime latestEvent = new DateTime(event.getRegisterDate()).plusMonths(recommendation.getMonthPeriod()).minusDays(DAYS_CHECK);

                    result = latestEvent.isAfterNow();

                    LOG.info("Month period check: {} for data: bike event date={}, event date={}, recommendation period={}",
                        result, event.getRegisterDate(), latestEvent.toDate(), recommendation.getMonthPeriod());

                    if (result) {
                        break;
                    }
                }
            }
        }

        if (recommendation.isMileagePeriod() && bikeEvent.isMileage()) {
            for (BikeEvent event : bikeEvents) {
                if (event.isMileage() && event.getEventTypes().contains(recommendation.getEventType())) {
                    result = (history.findCurrentMileage(bike) - event.getMileage() + MILEAGE_CHECK) <= recommendation.getMileagePeriod();

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
                if (event.isMth() && event.getEventTypes().contains(recommendation.getEventType())) {
                    result = (history.findCurrentMth(bike) - event.getMth() + MTH_CHECK) <= recommendation.getMthPeriod();

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

    public void setUserId(String userId) {
        setUser(userStore.get(userId));
    }
}
