package com.gruuf.web.actions.admin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.model.User;
import com.gruuf.services.BikeHistory;
import com.gruuf.services.Garage;
import com.gruuf.services.UserStore;
import com.gruuf.web.RbwActions;
import com.gruuf.web.interceptors.BikeHistoryAware;
import com.gruuf.web.interceptors.GarageAware;
import com.gruuf.web.interceptors.UserStoreAware;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.text.DateFormat;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.lang.System.out;
import static org.apache.struts2.interceptor.DateTextFieldInterceptor.DateWord.s;

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
public class Backup extends ActionSupport implements UserStoreAware, BikeHistoryAware, GarageAware {

    private static Logger LOG = LogManager.getLogger(Backup.class);
    private UserStore userStore;
    private BikeHistory bikeHistory;
    private Garage garage;

    private ByteArrayInputStream backupFile;

    public String execute() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

        String users = gson.toJson(userStore.list());
        LOG.debug("JSON users: {}", users);

        String bikes = gson.toJson(garage.list());
        LOG.debug("JSON bikes: {}", bikes);

        String eventTypes = gson.toJson(bikeHistory.listEventTypes());
        LOG.debug("JSON event types: {}", eventTypes);

        String events = gson.toJson(bikeHistory.list());
        LOG.debug("JSON events: {}", events);

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

            zos.putNextEntry(new ZipEntry("events.json"));
            zos.write(events.getBytes("UTF-8"));
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

    @Override
    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    @Override
    public void setBikeHistory(BikeHistory bikeHistory) {
        this.bikeHistory = bikeHistory;
    }

    @Override
    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    public InputStream getBackupFile() {
        return backupFile;
    }
}
