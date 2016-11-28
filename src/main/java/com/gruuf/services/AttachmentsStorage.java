package com.gruuf.services;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.gruuf.model.Attachment;
import com.gruuf.model.User;
import com.gruuf.web.GruufAuth;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.multipart.UploadedFile;

public class AttachmentsStorage extends Storable<Attachment> {

    private static final Logger LOG = LogManager.getLogger(AttachmentsStorage.class);

    private String bucketName;

    public AttachmentsStorage() {
        super(Attachment.class);
    }

    @Inject("gruuf.storage.bucketName")
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public Attachment storeAttachment(User currentUser, UploadedFile file, String fileName, String contentType) {
        Storage storage = StorageOptions.getDefaultInstance().getService();

        String uniqueName = currentUser.getId() + "/" + GruufAuth.generateUUID() + fileName.substring(fileName.lastIndexOf("."));
        LOG.debug("Unique file name [{}]", uniqueName);

        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uniqueName)
                .setContentType(contentType)
                .build();

        Object content = file.getContent();
        Blob blob = storage.create(blobInfo, (byte[]) content, Storage.BlobTargetOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ));

        LOG.debug("Stored object: {}", blob);

        Attachment attachment = Attachment.create(currentUser, fileName, uniqueName, blob).build();

        return put(attachment);
    }
}