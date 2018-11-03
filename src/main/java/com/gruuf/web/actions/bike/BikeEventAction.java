package com.gruuf.web.actions.bike;

import com.gruuf.auth.BikeRestriction;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.EventTypeDescriptor;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.util.TextParseUtil;
import com.opensymphony.xwork2.validator.annotations.DoubleRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.LongRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.dispatcher.multipart.UploadedFile;
import org.apache.struts2.interceptor.validation.SkipValidation;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.opensymphony.xwork2.Action.INPUT;

@Result(name = INPUT, location = "bike/bike-event-form")
@BikeRestriction
@InterceptorRef("defaultUploadWithMessages")
public class BikeEventAction extends BaseBikeAction implements Validateable, Preparable {

    private static final Logger LOG = LogManager.getLogger(BikeEventAction.class);

    private String bikeEventId;

    private String eventTypeIds;
    private String description;
    private Date registerDate;
    private Double cost;
    private Long mileage;
    private Long mth;
    private Long currentMileage;
    private Long currentMth;

    private List<UploadedFile> attachment = new ArrayList<>();
    private List<String> attachmentFileName = new ArrayList<>();
    private List<String> attachmentContentType = new ArrayList<>();

    @SkipValidation
    @Action("new-bike-event")
    public String execute() {
        currentMileage = bikeHistory.findCurrentMileage(selectedBike);
        currentMth = bikeHistory.findCurrentMth(selectedBike);
        return INPUT;
    }

    @SkipValidation
    @Action("edit-bike-event")
    public String edit() {
        if (StringUtils.isEmpty(bikeEventId)) {
            LOG.debug("New bike event");
        } else {
            BikeEvent bikeEvent = bikeHistory.get(bikeEventId);
            if (bikeEvent.isEditable()) {
                eventTypeIds = StringUtils.join(bikeEvent.getEventTypeIds(), ",");
                description = bikeEvent.getDescription().getContent();
                registerDate = bikeEvent.getRegisterDate();
                mileage = bikeEvent.getMileage();
                mth = bikeEvent.getMth();
                cost = bikeEvent.getCost();
            } else {
                LOG.debug("Bike event [{}] is not editable!", bikeEventId);
            }
        }

        currentMileage = bikeHistory.findCurrentMileage(selectedBike);
        currentMth = bikeHistory.findCurrentMth(selectedBike);
        return INPUT;
    }

    @Action("update-bike-event")
    public String registerBikeEvent() {
        if (StringUtils.isEmpty(bikeEventId)) {
            LOG.debug("Registering new bike event for bike {}", getBikeId());

            BikeEvent bikeEvent = BikeEvent.create(selectedBike, currentUser)
                    .withEventTypeId(TextParseUtil.commaDelimitedStringToSet(eventTypeIds))
                    .withDescription(description)
                    .withRegisterDate(registerDate)
                    .withCost(cost)
                    .withMileage(mileage)
                    .withMth(mth)
                    .build();

            LOG.debug("Storing new Bike Event {}", bikeEvent);
            bikeEvent = bikeHistory.put(bikeEvent);

            storeAttachments(bikeEvent);
        } else {
            BikeEvent oldBikeEvent = bikeHistory.get(bikeEventId);
            if (oldBikeEvent.isEditable()) {
                BikeEvent bikeEvent = BikeEvent.create(oldBikeEvent)
                        .withEventTypeId(TextParseUtil.commaDelimitedStringToSet(eventTypeIds))
                        .withDescription(description)
                        .withRegisterDate(registerDate)
                        .withCost(cost)
                        .withMileage(mileage)
                        .withMth(mth)
                        .build();

                LOG.debug("Storing new Bike Event {}", bikeEvent);
                bikeEvent = bikeHistory.put(bikeEvent);

                storeAttachments(bikeEvent);
            } else {
                LOG.debug("Bike event [{}] is not editable!", bikeEventId);
            }
        }

        LOG.debug("Returning to show bike {}", getBikeId());
        return TO_SHOW_BIKE;
    }

    private void storeAttachments(BikeEvent bikeEvent) {
        if (attachment != null && attachment.size() > 0 && StringUtils.isNoneEmpty(attachment.get(0).getName())) {
            LOG.debug("Storing attachments: {}", attachment);
            int index = 0;
            for (UploadedFile file : attachment) {
                storage.storeAttachment(bikeEvent, selectedBike, file, attachmentFileName.get(index), attachmentContentType.get(index));
                index++;
            }
        }
    }

    @Override
    public void prepare() throws Exception {
        calculateUsedSpace();
    }

    public List<EventTypeDescriptor> getEventTypesList() {
        return eventTypes.listApproved(currentUser);
    }

    public String getEventTypeIds() {
        return eventTypeIds;
    }

    @RequiredStringValidator(key = "bikeEvent.eventTypeIsRequired")
    @StringLengthFieldValidator(minLength = "4", key = "bikeEvent.eventTypeIsRequired")
    public void setEventTypeIds(String eventTypeIds) {
        this.eventTypeIds = eventTypeIds;
    }

    public String getDescription() {
        return description;
    }

    @RequiredStringValidator(key = "bikeEvent.descriptionTooShort")
    @StringLengthFieldValidator(minLength = "4", key = "bikeEvent.descriptionTooShort")
    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    @RequiredFieldValidator(key = "bikeEvent.registerDateIsRequired")
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Double getCost() {
        return cost;
    }

    @DoubleRangeFieldValidator(minExclusive = "0")
    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Long getMileage() {
        return mileage;
    }

    @RequiredFieldValidator(key = "bikeEvent.mileageIsRequired")
    @LongRangeFieldValidator(min = "0", key = "bike.providedMileageMustBeZeroOrGreater")
    public void setMileage(Long mileage) {
        this.mileage = mileage;
    }

    public Long getMth() {
        return mth;
    }

    public void setMth(Long mth) {
        this.mth = mth;
    }

    public Long getCurrentMileage() {
        return currentMileage;
    }

    public String getCurrentMileageHelp() {
        if (currentMileage == null) {
            return getText("bike.currentMileageNotDefined");
        }
        return getText("bike.currentMileageInKmIs");
    }

    public Long getCurrentMth() {
        return currentMth;
    }

    public String getCurrentMthHelp() {
        if (currentMth == null) {
            return getText("bike.currentMthNotDefined");
        }
        return getText("bike.currentMthIs");
    }

    public String getSpaceLeftHelp() {
        String spaceLeft = NumberFormat.getNumberInstance(currentUserLocale.toLocale()).format(getSpaceLeft());
        return String.format(currentUserLocale.toLocale(), "%s: %s %s", getText("bikeEvent.spaceLeft"), spaceLeft, getText("bikeEvent.kiloBytes"));
    }

    public String getBikeEventId() {
        return bikeEventId;
    }

    public void setBikeEventId(String bikeEventId) {
        this.bikeEventId = bikeEventId;
    }

    public List<UploadedFile> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<UploadedFile> attachment) {
        this.attachment = attachment;
    }

    public List<String> getAttachmentFileName() {
        return attachmentFileName;
    }

    public void setAttachmentFileName(List<String> attachmentFileName) {
        this.attachmentFileName = attachmentFileName;
    }

    public List<String> getAttachmentContentType() {
        return attachmentContentType;
    }

    public void setAttachmentContentType(List<String> attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }
}
