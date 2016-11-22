package com.gruuf.web.actions.bike;

import com.google.cloud.storage.*;
import com.gruuf.struts2.gae.dispatcher.multipart.GaeUploadedFile;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.dispatcher.multipart.UploadedFile;

import java.util.ArrayList;
import java.util.List;

import static com.opensymphony.xwork2.Action.INPUT;

@Result(name = INPUT, location = "bike/attachments")
@InterceptorRef("gruufDefaultUploadDev")
public class AttachmentsAction extends BaseBikeAction {

    private UploadedFile attachment;
    private String attachmentFileName;
    private String attachmentContentType;

    public String execute() throws Exception {
        return SUCCESS;
    }

    @Action("upload")
    public String upload() throws Exception {
        List<Acl> acls = new ArrayList<>();
        acls.add(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        Storage storage = StorageOptions.getDefaultInstance().getService();

        BlobInfo blobInfo = BlobInfo.newBuilder("gruuf-webapp.appspot.com", attachmentFileName)
                .setAcl(acls)
                .setContentType(attachmentContentType)
                .build();

        Object content = attachment.getContent();
        Blob blob = storage.create(blobInfo, (byte[]) content);

        System.out.println(blob.getMediaLink());
        System.out.println(blob.getSelfLink());

        return INPUT;
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
}
