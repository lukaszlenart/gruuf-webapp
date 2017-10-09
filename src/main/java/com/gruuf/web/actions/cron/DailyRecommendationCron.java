package com.gruuf.web.actions.cron;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.gruuf.auth.Anonymous;
import com.gruuf.model.Bike;
import com.gruuf.services.Garage;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;

import static com.opensymphony.xwork2.Action.SUCCESS;

@Anonymous
@Result(name = SUCCESS, type = "httpheader", params = {"status", "200"})
public class DailyRecommendationCron extends BaseAction {

    private static final Logger LOG = LogManager.getLogger(DailyRecommendationCron.class);

    private Garage garage;

    public String execute() {
        Queue queue = QueueFactory.getDefaultQueue();

        for (Bike bike : garage.list()) {
            LOG.info("Creating daily recommendation check task for {} ({})", bike.getName(), bike.getId());
            queue.addAsync(TaskOptions.Builder
                    .withUrl("/tasks/daily-recommendation-check")
                    .taskName(bike.getName().replaceAll(" ", "_") + "-" + bike.getId())
                    .param("bikeId", bike.getId())
            );
        }

        return SUCCESS;
    }

    @Inject
    public void setGarage(Garage garage) {
        this.garage = garage;
    }
}
