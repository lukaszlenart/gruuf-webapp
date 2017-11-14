package com.gruuf.web.actions.admin;

import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.services.BikeHistory;
import com.gruuf.services.EventTypes;
import com.gruuf.services.Garage;
import com.gruuf.services.UserStore;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.DefaultJSONWriter;
import org.apache.struts2.json.JSONWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Tokens(Token.ADMIN)
@Result(
        name = "success",
        type = "stream",
        params = {
                "inputName", "backupFile",
                "contentType", "application/zip",
                "contentCharSet", "UTF-8",
                "contentDisposition", "attachment;filename=\"backup.zip\""
        }
)
public class BackupAction extends BaseAction {

    private static Logger LOG = LogManager.getLogger(BackupAction.class);
    private UserStore userStore;
    private BikeHistory bikeHistory;
    private Garage garage;
    private EventTypes eventTypes;

    private ByteArrayInputStream backupFile;

    public String execute() throws Exception {
        JSONWriter writer = new DefaultJSONWriter();
        writer.setDateFormatter("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        String users = writer.write(userStore.list());
        LOG.debug("JSON users: {}", users);

        String bikes = writer.write(garage.list());
        LOG.debug("JSON bikes: {}", bikes);

        String eventTypes = writer.write(this.eventTypes.list());
        LOG.debug("JSON event types: {}", eventTypes);

        String bikeEvents = writer.write(bikeHistory.list());
        LOG.debug("JSON bikeEvents: {}", bikeEvents);

        ByteArrayOutputStream backup = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(backup);
        try {
            zos.putNextEntry(new ZipEntry("users.json"));
            zos.write(users.getBytes("UTF-8"));
            zos.closeEntry();

            zos.putNextEntry(new ZipEntry("bikes.json"));
            zos.write(bikes.getBytes("UTF-8"));
            zos.closeEntry();

            zos.putNextEntry(new ZipEntry("event-types.json"));
            zos.write(eventTypes.getBytes("UTF-8"));
            zos.closeEntry();

            zos.putNextEntry(new ZipEntry("bike-events.json"));
            zos.write(bikeEvents.getBytes("UTF-8"));
            zos.closeEntry();

            zos.finish();

            backupFile = new ByteArrayInputStream(backup.toByteArray());

            zos.close();
            backup.close();

        } catch (IOException e) {
            LOG.error("Cannot create backup!", e);
        }

        return SUCCESS;
    }

    @Inject
    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    @Inject
    public void setBikeHistory(BikeHistory bikeHistory) {
        this.bikeHistory = bikeHistory;
    }

    @Inject
    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    @Inject
    public void setEventTypes(EventTypes eventTypes) {
        this.eventTypes = eventTypes;
    }

    public InputStream getBackupFile() {
        return backupFile;
    }
}
