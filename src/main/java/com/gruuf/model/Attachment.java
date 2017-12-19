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
    @Index
    private Ref<BikeEvent> bikeEvent;

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

    public Ref<BikeEvent> getBikeEvent() {
        return bikeEvent;
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
                ", bikeEvent=" + bikeEvent +
                ", bucketName='" + bucketName + '\'' +
                ", uniqueName='" + uniqueName + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                ", size=" + size +
                ", contentType='" + contentType + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public static AttachmentBuilder create(User owner) {
        return new AttachmentBuilder(owner);
    }

    public static AttachmentBuilder create(BikeEvent bikeEvent) {
        return new AttachmentBuilder(bikeEvent);
    }

    public static class AttachmentBuilder {

        private Attachment target;

        AttachmentBuilder(User owner) {
            target = new Attachment();

            target.id = GruufAuth.generateUUID();
            target.owner = Ref.create(owner);
            target.timestamp = new Date();
        }

        public AttachmentBuilder(BikeEvent bikeEvent) {
            target = new Attachment();

            target.id = GruufAuth.generateUUID();
            target.bikeEvent = Ref.create(bikeEvent);
            target.timestamp = new Date();
        }

        public AttachmentBuilder withBike(Bike bike) {
            target.bike = Ref.create(bike);
            return this;
        }

        public AttachmentBuilder withFileName(String fileName) {
            target.originalFileName = fileName;
            return this;
        }

        public AttachmentBuilder withUniqueName(String uniqueName) {
            target.uniqueName = uniqueName;
            return this;
        }

        public AttachmentBuilder withBlob(Blob blob) {
            target.bucketName = blob.getBucket();
            target.size = blob.getSize();
            target.contentType = blob.getContentType();
            return this;
        }

        public Attachment build() {
            return target;
        }

    }
}
