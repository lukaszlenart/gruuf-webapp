package com.gruuf.services;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.gruuf.model.Attachment;
import com.gruuf.model.Bike;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.User;
import com.gruuf.web.GruufAuth;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.multipart.UploadedFile;

import java.util.List;

public class AttachmentsStorage extends Reindexable<Attachment> {

    private static final Logger LOG = LogManager.getLogger(AttachmentsStorage.class);

    private String bucketName;
    private Storage storage;

    private Garage garage;

    public AttachmentsStorage() {
        super(Attachment.class);
        storage = StorageOptions.getDefaultInstance().getService();
    }

    @Override
    protected boolean shouldReindex() {
        // change to true when migrating data
        return false;
    }

    @Inject("gruuf.storage.bucketName")
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    @Inject
    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    public List<Attachment> findByBike(Bike bike) {
        return filter("bike", bike)
                .order("timestamp")
                .list();
    }

    public Attachment storeAttachment(User currentUser, Bike bike,  UploadedFile file, String fileName, String contentType) {
        String uniqueName = generateUniqueName(bike, fileName);

        Blob blob = storeFile(file, contentType, uniqueName);

        Attachment attachment = Attachment
                .create(currentUser)
                .withBike(bike)
                .withFileName(fileName)
                .withUniqueName(uniqueName)
                .withBlob(blob).build();

        return updateSizeAndPut(bike, blob, attachment);
    }

    public Attachment storeAttachment(BikeEvent bikeEvent, Bike bike, UploadedFile file, String fileName, String contentType) {
        String uniqueName = generateUniqueName(bike, fileName);

        Blob blob = storeFile(file, contentType, uniqueName);

        Attachment attachment = Attachment
                .create(bikeEvent)
                .withBike(bike)
                .withFileName(fileName)
                .withUniqueName(uniqueName)
                .withBlob(blob)
                .build();

        return updateSizeAndPut(bike, blob, attachment);
    }

    private Attachment updateSizeAndPut(Bike bike, Blob blob, Attachment attachment) {
        garage.updateSpaceUsedBy(bike, blob.getSize());

        return put(attachment);
    }

    private String generateUniqueName(Bike bike, String fileName) {
        String uniqueName = bike.getId() + "/" + GruufAuth.generateUUID() + fileName.substring(fileName.lastIndexOf("."));
        LOG.debug("Unique file name [{}]", uniqueName);
        return uniqueName;
    }

    private Blob storeFile(UploadedFile file, String contentType, String uniqueName) {
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uniqueName)
                .setContentType(contentType)
                .build();

        Object content = file.getContent();
        Blob blob = storage.create(blobInfo, (byte[]) content, Storage.BlobTargetOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ));

        LOG.debug("Stored object: {}", blob);
        return blob;
    }

    public long spaceUsedBy(Bike bike) {
        long spaceUsed = 0;
        Bike actual = garage.get(bike.getId());
        if (actual.getSpaceUsed() != null) {
            spaceUsed = actual.getSpaceUsed();
        }
        return spaceUsed;
    }

    public boolean delete(Attachment attachment) {
        BlobId blobId = BlobId.of(attachment.getBucketName(), attachment.getUniqueName());
        boolean result = storage.delete(blobId);
        if (result) {
            LOG.debug("Dropping entity {}", attachment);
            drop(attachment.getId());
        }
        return result;
    }
}
