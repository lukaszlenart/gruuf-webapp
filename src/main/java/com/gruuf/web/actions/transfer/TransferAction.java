package com.gruuf.web.actions.transfer;

import com.gruuf.GruufConstants;
import com.gruuf.auth.BikeRestriction;
import com.gruuf.model.Bike;
import com.gruuf.model.BikeMetadataDescriptor;
import com.gruuf.model.BikeTransfer;
import com.gruuf.services.BikeTransfers;
import com.gruuf.services.MailBox;
import com.gruuf.web.actions.bike.BaseBikeAction;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;

import static com.googlecode.objectify.ObjectifyService.ofy;
import static com.gruuf.web.GlobalResult.GARAGE;
import static com.gruuf.web.actions.transfer.TransferAction.PREPARE_INPUT;
import static com.opensymphony.xwork2.Action.INPUT;

@Results({
    @Result(name = INPUT, location = "transfer/start-input"),
    @Result(name = PREPARE_INPUT, location = "transfer/prepare-input")
})
@BikeRestriction
@InterceptorRef("defaultWithMessages")
public class TransferAction extends BaseBikeAction {

    public static final String PREPARE_INPUT = "prepare-input";

    private static Logger LOG = LogManager.getLogger(TransferAction.class);

    @Inject
    private MailBox mailBox;
    @Inject
    private BikeTransfers bikeTransfers;
    @Inject(GruufConstants.HOST_URL)
    private String hostUrl;

    private String emailAddress;

    @Action("start")
    @SkipValidation
    public String execute() {
        return INPUT;
    }

    @Action("prepare")
    public String prepareTransfer() {
        LOG.debug("Preparing transfer of {} to {}", selectedBike, emailAddress);
        return PREPARE_INPUT;
    }

    @Action("perform")
    public String performTransfer() {
        LOG.debug("Performing transfer of {} to {}", selectedBike, emailAddress);

        ofy().transact(() -> {
            Bike bike = garage.get(selectedBike.getId());
            bike = bike.markAsUnderTransfer();
            bike = garage.put(bike);

            BikeTransfer bikeTransfer = bikeTransfers.register(bike, emailAddress);

            String bikeFullName = new BikeMetadataDescriptor(selectedBike.getBikeMetadata()).getFullName();
            String acceptanceLink = hostUrl + "/transfer/accept?token=" + bikeTransfer.getId();

            String message = "Please accept bike transfer:\n" + bikeFullName + "\n\n" + acceptanceLink;

            mailBox.notifyOwner(emailAddress, emailAddress, getText("transfer.subject"), message);
        });

        return GARAGE;
    }

    public String getBikeId() {
        return selectedBike.getId();
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    @RequiredStringValidator(key = "transfer.emailIsRequired")
    @EmailValidator(key = "general.addressEmailIsInvalid")
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
