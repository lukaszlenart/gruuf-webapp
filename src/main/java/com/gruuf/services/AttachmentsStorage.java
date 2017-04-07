package com.gruuf.services;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.gruuf.model.Attachment;
import com.gruuf.model.Bike;
import com.gruuf.model.User;
import com.gruuf.web.GruufAuth;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.multipart.UploadedFile;

import java.util.List;

public class AttachmentsStorage extends Storable<Attachment> {

    private static final Logger LOG = LogManager.getLogger(AttachmentsStorage.class);

    private String bucketName;
    private Storage storage;

    public AttachmentsStorage() {
        super(Attachment.class);
        storage = StorageOptions.getDefaultInstance().getService();
    }

    @Inject("gruuf.storage.bucketName")
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public List<Attachment> findByBike(Bike bike) {
        return filter("bike", bike)
                .order("timestamp")
                .list();
    }

    public Attachment storeAttachment(User currentUser, Bike bike,  UploadedFile file, String fileName, String contentType) {
        String uniqueName = bike.getId() + "/" + GruufAuth.generateUUID() + fileName.substring(fileName.lastIndexOf("."));
        LOG.debug("Unique file name [{}]", uniqueName);

        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uniqueName)
                .setContentType(contentType)
                .build();

        Object content = file.getContent();
        Blob blob = storage.create(blobInfo, (byte[]) content, Storage.BlobTargetOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ));

        LOG.debug("Stored object: {}", blob);

        Attachment attachment = Attachment.create(currentUser, bike, fileName, uniqueName, blob).build();

        return put(attachment);
    }

    public long countSpaceByUser(User currentUser) {
        long sum = 0;
        for (Attachment attachment : findBy("owner", currentUser)) {
            sum = sum + attachment.getSize();
        }
        return sum;
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
