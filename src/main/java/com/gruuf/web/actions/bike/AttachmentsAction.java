package com.gruuf.web.actions.bike;

import com.gruuf.GruufConstants;
import com.gruuf.auth.BikeRestriction;
import com.gruuf.model.Attachment;
import com.gruuf.model.AttachmentDescriptor;
import com.gruuf.services.AttachmentsStorage;
import com.gruuf.struts2.gae.dispatcher.multipart.GaeUploadedFile;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.dispatcher.multipart.UploadedFile;

import java.util.ArrayList;
import java.util.List;

import static com.opensymphony.xwork2.Action.INPUT;

@Result(name = INPUT, location = "bike/attachments")
@InterceptorRef("gruufDefaultUploadDev")
@BikeRestriction
public class AttachmentsAction extends BaseBikeAction {

    private static final Logger LOG = LogManager.getLogger(AttachmentsAction.class);

    private AttachmentsStorage storage;
    private String rootUrl;
    private Long totalAllowedSpace;

    private UploadedFile attachment;
    private String attachmentFileName;
    private String attachmentContentType;

    public String execute() throws Exception {
        return SUCCESS;
    }

    @Action("upload")
    public String upload() throws Exception {
        LOG.debug("Storing attachment [{}] of content type [{}] as [{}]", attachment, attachmentContentType, attachmentFileName);
        storage.storeAttachment(currentUser, attachment, attachmentFileName, attachmentContentType);

        return INPUT;
    }

    @Inject
    public void setStorage(AttachmentsStorage storage) {
        this.storage = storage;
    }

    @Inject(GruufConstants.STORAGE_ROOT_URL)
    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    @Inject(GruufConstants.STORAGE_TOTAL_ALLOWED_SPACE)
    public void setTotalAllowedSpace(String totalAllowedSpace) {
        this.totalAllowedSpace = Long.parseLong(totalAllowedSpace);
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

    public List<AttachmentDescriptor> getAttachments() {
        List<AttachmentDescriptor> attachments = new ArrayList<>();

        for (Attachment attachment : storage.findBy("owner", currentUser)) {
            attachments.add(new AttachmentDescriptor(rootUrl, attachment));
        }

        return attachments;
    }

    public long getSpaceLeft() {
        long usedSpace = storage.countSpaceByUser(currentUser);
        long spaceLeft = totalAllowedSpace - usedSpace;
        return spaceLeft/1024;
    }
}
