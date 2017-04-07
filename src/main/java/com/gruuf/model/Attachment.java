package com.gruuf.model;

import com.google.cloud.storage.Blob;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.gruuf.web.GruufAuth;

import java.util.Date;

@Entity
public class Attachment {

    @Id
    private String id;
    private Ref<User> owner;
    @Index
    private Ref<Bike> bike;

    private String bucketName;
    private String uniqueName;

    private String originalFileName;
    private Long size;
    private String contentType;

    @Index
    private Date timestamp;

    public Attachment() {
    }

    public String getId() {
        return id;
    }

    public Ref<User> getOwner() {
        return owner;
    }

    public Ref<Bike> getBike() {
        return bike;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public Long getSize() {
        return size;
    }

    public String getContentType() {
        return contentType;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id='" + id + '\'' +
                ", owner=" + owner +
                ", bike=" + bike +
                ", bucketName='" + bucketName + '\'' +
                ", uniqueName='" + uniqueName + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                ", size=" + size +
                ", contentType='" + contentType + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public static AttachmentBuilder create(User owner, Bike bike, String fileName, String uniqueName, Blob blob) {
        return new AttachmentBuilder(owner, bike, fileName, uniqueName, blob);
    }

    public static class AttachmentBuilder {

        private Attachment target;

        AttachmentBuilder(User owner, Bike bike, String fileName, String uniqueName, Blob blob) {
            target = new Attachment();

            target.id = GruufAuth.generateUUID();
            target.owner = Ref.create(owner);
            target.bike = Ref.create(bike);
            target.bucketName = blob.getBucket();

            target.originalFileName = fileName;
            target.uniqueName = uniqueName;
            target.timestamp = new Date();
            target.size = blob.getSize();
            target.contentType = blob.getContentType();
        }

        public Attachment build() {
            return target;
        }
    }
}
