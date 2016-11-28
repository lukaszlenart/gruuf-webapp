package com.gruuf.model;

import com.googlecode.objectify.Ref;

import java.util.Date;

public class AttachmentDescriptor {

    private String rootUrl;
    private Attachment attachment;

    public AttachmentDescriptor(String rootUrl, Attachment attachment) {
        this.rootUrl = rootUrl;
        this.attachment = attachment;
    }

    public String getId() {
        return attachment.getId();
    }

    public Ref<User> getOwner() {
        return attachment.getOwner();
    }

    public String getBucketName() {
        return attachment.getBucketName();
    }

    public String getLink() {
        return rootUrl + attachment.getBucketName() + "/" + attachment.getUniqueName();
    }

    public String getOriginalFileName() {
        return attachment.getOriginalFileName();
    }

    public Long getSize() {
        return attachment.getSize()/1024;
    }

    public String getContentType() {
        return attachment.getContentType();
    }

    public Date getTimestamp() {
        return attachment.getTimestamp();
    }

}
