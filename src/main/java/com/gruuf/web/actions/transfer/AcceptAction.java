package com.gruuf.web.actions.transfer;

import com.gruuf.auth.Anonymous;
import com.gruuf.model.Bike;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.BikeTransfer;
import com.gruuf.model.User;
import com.gruuf.services.BikeHistory;
import com.gruuf.services.BikeTransfers;
import com.gruuf.services.Garage;
import com.gruuf.services.UserStore;
import com.gruuf.web.actions.bike.BaseBikeAction;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.InterceptorRef;

import java.util.Collections;
import java.util.Date;

import static com.googlecode.objectify.ObjectifyService.ofy;
import static com.gruuf.web.GlobalResult.GARAGE;

@Anonymous
@InterceptorRef("defaultWithMessages")
public class AcceptAction extends BaseBikeAction {

    private static Logger LOG = LogManager.getLogger(AcceptAction.class);

    private String token;

    @Inject
    private BikeTransfers bikeTransfers;
    @Inject
    private Garage garage;
    @Inject
    private UserStore userStore;
    @Inject
    private BikeHistory history;

    public String execute() {
        if (StringUtils.isBlank(token)) {
            return result();
        }

        final BikeTransfer bikeTransfer = bikeTransfers.get(token);

        if (bikeTransfer == null) {
            addActionError(getText("transfer.tokenIsInvalid"));
        } else {
            final User user = userStore.findByEmail(bikeTransfer.getEmailAddress());
            final Bike bikeUnderTransfer = bikeTransfer.getBike().get();
            Long bikeMileage = history.findCurrentMileage(bikeUnderTransfer);
            Long bikeMth = history.findCurrentMth(bikeUnderTransfer);

            final String transferEventTypeId = eventTypes.getTransferEventType().getId();

            ofy().transact(() -> {
                User newUser = user;
                if (newUser == null) {
                    newUser = User.create()
                        .withEmail(bikeTransfer.getEmailAddress())
                        .build();
                    newUser = userStore.resetPassword(newUser);
                    sendNewPassword(newUser);
                }

                String oldOwnerName = bikeUnderTransfer.getOwner().getFullName();
                Bike bike = bikeUnderTransfer.transferTo(newUser);
                garage.put(bike);

                bikeTransfers.put(bikeTransfer.markAsDone());

                BikeEvent event = BikeEvent.create(bike, newUser)
                    .withDescription(getText("transfer.bikeTransferredFrom", new String[]{ oldOwnerName }))
                    .withEventTypeId(Collections.singleton(transferEventTypeId))
                    .withRegisterDate(new Date())
                    .withMileage(bikeMileage)
                    .withMth(bikeMth)
                    .markAsSystem()
                    .build();

                history.put(event);
            });
        }

        return result();
    }

    private String result() {
        if (currentUser == null) {
            LOG.debug("User is not logged in, redirecting to login page");
            return LOGIN;
        }
        LOG.debug("User is logged in, redirecting to garage page");
        return GARAGE;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
