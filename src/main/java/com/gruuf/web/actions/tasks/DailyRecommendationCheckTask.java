package com.gruuf.web.actions.tasks;

import com.gruuf.auth.Anonymous;
import com.gruuf.model.Bike;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.BikeRecommendation;
import com.gruuf.model.BikeStatus;
import com.gruuf.model.MissingRecommendation;
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

import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
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

    private static final int DAYS_CHECK = 14;
    private static final long MILEAGE_CHECK = 1000L;
    private static final long MTH_CHECK = 10L;

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
            List<MissingRecommendation> missingRecommendations = listMissingRecommendations(bike);

            if (missingRecommendations.size() > 0) {
                LOG.info("Found missing recommendations");
                if (body == null) {
                    body = new StringBuilder("# ");
                }

                // FIXME: implement LocaleProvider instead, once fixed in Struts
                ActionContext.getContext().setLocale(currentUser.getUserLocale().toLocale());

                body.append(getText("recommendations.followingRecommendationsAreGoingToExpire")).append("\n\n")
                    .append("## ").append(bike.getName()).append(":\n\n");

                for (MissingRecommendation recommendation : missingRecommendations) {
                    if (recommendation.isNotify()) {
                        String eventName = recommendation.getEventType().getNames().get(currentUser.getUserLocale());

                        LOG.debug(
                            "Missing recommendation {} result: {}={}/{}={}/{}={}",
                            recommendation.getDescription(),
                            recommendation.isDateExpiration(),
                            recommendation.getExpirationDate(),
                            recommendation.isMileageExpiration(),
                            recommendation.getExpirationMileage(),
                            recommendation.isMthExpiration(),
                            recommendation.getExpirationMth()
                        );
                        body.append("### ").append(eventName).append(":\n\n");
                        if (recommendation.getDescription().getContent().trim().startsWith("-")) {
                            body.append("  ");
                            body = appendMissingRecommendation(body, recommendation);
                        } else {
                            body.append("  - ");
                            body = appendMissingRecommendation(body, recommendation);
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

    private StringBuilder appendMissingRecommendation(StringBuilder body, MissingRecommendation recommendation) {
        body.append(recommendation.getDescription().getContent());
        if (recommendation.isDateExpiration()) {
            body
                .append("; ")
                .append(getText("recommendations.expireDate"))
                .append(": ")
                .append(recommendation.getExpirationDate().toString(getUserDateFormat(), getCurrentUserLocale().toLocale()));
        } else if(recommendation.isMileageExpiration()) {
            body
                .append("; ")
                .append(getText("recommendations.expireMileage"))
                .append(": ")
                .append(NumberFormat.getNumberInstance(getCurrentUserLocale().toLocale()).format(recommendation.getExpirationMileage()))
                .append(" ").append(getText("general.km"));
        } else if(recommendation.isMthExpiration()) {
            body
                .append("; ")
                .append(getText("recommendations.expireMth"))
                .append(": ")
                .append(NumberFormat.getNumberInstance(getCurrentUserLocale().toLocale()).format(recommendation.getExpirationMth()))
                .append(" ").append(getText("general.mth"));
        }
        return body.append("\n\n");
    }

    private List<Bike> listBikes() {
        return garage
            .findByOwner(currentUser)
            .stream()
            .filter(bike -> bike.getStatus() == BikeStatus.NORMAL)
            .collect(Collectors.toList());
    }

    private List<MissingRecommendation> listMissingRecommendations(Bike bike) {

        List<BikeRecommendation> recommendations = this.recommendations.listFor(bike.getBikeMetadata());
        List<BikeEvent> bikeEvents = history.listByBike(bike);

        List<MissingRecommendation> missingRecommendations = new ArrayList<>();
        for (BikeRecommendation recommendation : recommendations) {
            boolean missingRecommendation = true;
            PeriodResult result = null;
            for (BikeEvent bikeEvent : bikeEvents) {
                if (bikeEvent.getEventTypes().contains(recommendation.getEventType())) {
                    result = matchesPeriod(bike, bikeEvent, recommendation, bikeEvents);
                    if (result.matches()) {
                        missingRecommendation = false;
                        break;
                    }
                }
            }

            if (missingRecommendation) {
                missingRecommendations.add(MissingRecommendation.of(recommendation, result));
            }
        }

        return missingRecommendations;
    }

    private PeriodResult matchesPeriod(Bike bike, BikeEvent bikeEvent, BikeRecommendation recommendation, List<BikeEvent> bikeEvents) {
        PeriodResult result = PeriodResult.noMatch();

        if (recommendation.isMonthPeriod()) {
            for (BikeEvent event : bikeEvents) {
                if (event.getEventTypes().contains(recommendation.getEventType())) {

                    DateTime latestEvent = new DateTime(event.getRegisterDate()).plusMonths(recommendation.getMonthPeriod()).minusDays(DAYS_CHECK);

                    result = result.withResult(latestEvent.isAfterNow()).withExpiresDate(latestEvent);

                    LOG.info("Month period check: {} for data: bike event date={}, event date={}, recommendation period={}",
                        result, event.getRegisterDate(), latestEvent.toDate(), recommendation.getMonthPeriod());

                    if (result.matches()) {
                        break;
                    }
                }
            }
        }

        if (recommendation.isMileagePeriod() && bikeEvent.isMileage()) {
            for (BikeEvent event : bikeEvents) {
                if (event.isMileage() && event.getEventTypes().contains(recommendation.getEventType())) {
                    Long expiresMileage = history.findCurrentMileage(bike) - event.getMileage() + MILEAGE_CHECK;
                    result = result.withResult(expiresMileage <= recommendation.getMileagePeriod()).withMileage(expiresMileage);

                    LOG.info("Mileage period check: {} for data: bike mileage={}, event mileage={}, recommendation mileage={}",
                        result, bikeEvent.getMileage(), event.getMileage(), recommendation.getMileagePeriod());

                    if (result.matches()) {
                        break;
                    }
                }
            }
        }

        if (recommendation.isMthPeriod() && bikeEvent.isMth()) {
            for (BikeEvent event : bikeEvents) {
                if (event.isMth() && event.getEventTypes().contains(recommendation.getEventType())) {
                    Long expiresMth = history.findCurrentMth(bike) - event.getMth() + MTH_CHECK;
                    result = result.withResult(expiresMth <= recommendation.getMthPeriod()).withMth(expiresMth);

                    LOG.info("Mth period check: {} for data: bike mth={}, event mth={}, recommendation mth={}",
                        result, bikeEvent.getMth(), event.getMth(), recommendation.getMthPeriod());

                    if (result.matches()) {
                        break;
                    }
                }
            }
        }

        return result;
    }

    public void setUserId(String userId) {
        withUser(userStore.get(userId));
    }

    public static class PeriodResult {
        private boolean match;
        private DateTime expirationDate;
        private Long expirationMileage;
        private Long expirationMth;

        public static PeriodResult noMatch() {
            return new PeriodResult(false);
        }

        public PeriodResult(boolean match) {
            this.match = match;
        }

        public PeriodResult withResult(boolean matches) {
            this.match = matches;
            return this;
        }

        public PeriodResult withExpiresDate(DateTime expiresDate) {
            this.expirationDate = expiresDate;
            return this;
        }

        public PeriodResult withMileage(Long expiresMileage) {
            this.expirationMileage = expiresMileage;
            return this;
        }

        public PeriodResult withMth(Long expiresMth) {
            this.expirationMth = expiresMth;
            return this;
        }

        public boolean matches() {
            return match;
        }

        public DateTime getExpirationDate() {
            return expirationDate;
        }

        public Long getExpirationMileage() {
            return expirationMileage;
        }

        public Long getExpirationMth() {
            return expirationMth;
        }

        @Override
        public String toString() {
            return "PeriodResult{" +
                "match=" + match +
                ", expirationDate=" + expirationDate +
                ", expirationMileage=" + expirationMileage +
                ", expirationMth=" + expirationMth +
                '}';
        }
    }
}
