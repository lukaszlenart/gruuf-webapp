package com.gruuf.web.actions.bike;

import com.gruuf.auth.BikeRestriction;
import com.gruuf.model.Attachment;
import com.gruuf.model.AttachmentDescriptor;
import com.gruuf.struts2.gae.dispatcher.multipart.GaeUploadedFile;
import com.opensymphony.xwork2.Preparable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.dispatcher.multipart.UploadedFile;

import java.util.ArrayList;
import java.util.List;

import static com.gruuf.web.actions.bike.AttachmentsAction.REDIRECT_TO_ATTACHMENTS;
import static com.opensymphony.xwork2.Action.INPUT;

@Results({
        @Result(name = INPUT, location = "bike/attachments"),
        @Result(name = REDIRECT_TO_ATTACHMENTS, type = "redirectAction", location = "attachments",
                params = { "bikeId", "${bikeId}" })
})
@InterceptorRef("defaultUploadWithMessages")
@BikeRestriction
public class AttachmentsAction extends BaseBikeAction implements Preparable {

    private static final Logger LOG = LogManager.getLogger(AttachmentsAction.class);

    public static final String REDIRECT_TO_ATTACHMENTS = "redirect-to-attachments";

    private UploadedFile attachment;
    private String attachmentFileName;
    private String attachmentContentType;

    private String attachmentId;

    public String execute() throws Exception {
        return INPUT;
    }

    @Action("upload")
    public String upload() throws Exception {
        LOG.debug("Storing attachment [{}] of content type [{}] as [{}]", attachment, attachmentContentType, attachmentFileName);
        storage.storeAttachment(currentUser, selectedBike, attachment, attachmentFileName, attachmentContentType);

        return REDIRECT_TO_ATTACHMENTS;
    }

    @Action("delete")
    public String delete() throws Exception {
        Attachment attachment = storage.get(attachmentId);
        if (attachment != null) {
            if (!storage.delete(attachment)) {
                addActionError(getText("bikeEvent.cannotDeleteAttachment"));
            }
        } else {
            addActionError(getText("bikeEvent.noAttachmentWithGivenId"));
        }
        return REDIRECT_TO_ATTACHMENTS;
    }

    @Override
    public void prepare() throws Exception {
        calculateUsedSpace();
    }

    public UploadedFile getAttachment() {
        return attachment;
    }

    public void setAttachment(GaeUploadedFile attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentFileName() {
        return attachmentFileName;
    }

    public void setAttachmentFileName(String attachmentFileName) {
        this.attachmentFileName = attachmentFileName;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public List<AttachmentDescriptor> getAttachments() {
        List<AttachmentDescriptor> attachments = new ArrayList<>();

        for (Attachment attachment : storage.findByBike(selectedBike)) {
            attachments.add(new AttachmentDescriptor(storageRootUrl, attachment));
        }

        return attachments;
    }

}
