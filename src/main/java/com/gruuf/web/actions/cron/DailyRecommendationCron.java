package com.gruuf.web.actions.cron;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskAlreadyExistsException;
import com.google.appengine.api.taskqueue.TaskHandle;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.gruuf.auth.Anonymous;
import com.gruuf.model.User;
import com.gruuf.services.Garage;
import com.gruuf.services.UserStore;
import com.gruuf.web.GruufAuth;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

import static com.opensymphony.xwork2.Action.SUCCESS;

@Anonymous
@Result(name = SUCCESS, type = "httpHeader", params = {"status", "200"})
public class DailyRecommendationCron extends BaseAction {

    private static final Logger LOG = LogManager.getLogger(DailyRecommendationCron.class);

    @Inject
    private UserStore userStore;
    @Inject
    private Garage garage;

    public String execute() {
        Queue queue = QueueFactory.getDefaultQueue();

        LOG.info("Purging old tasks");
        queue.purge();

        List<User> users = userStore.list();

        for (User user : users) {
            if (user.isNotify() & garage.findByOwner(user).size() > 0) {
                createTask(queue, user);
            }
        }

        return SUCCESS;
    }

    private void createTask(Queue queue, User user) {
        LOG.info("Creating daily recommendation check task for user {}", user.getId());
        String taskName = "userId-" + user.getId() + "-" + GruufAuth.generateUUID();
        try {
            TaskHandle handle = queue.add(TaskOptions.Builder
                    .withUrl("/tasks/daily-recommendation-check")
                    .taskName(taskName)
                    .param("userId", user.getId())
            );
            LOG.info("A new task was added to the queue: {}", handle);
        } catch (TaskAlreadyExistsException e) {
            LOG.error(new ParameterizedMessage("Task already exists {}, deleting it", taskName), e);
            if (queue.deleteTask(taskName)) {
                LOG.info("Deleted task: {}", taskName);
            } else {
                LOG.error("Cannot delete task: {}", taskName);
            }
        }
    }
}
