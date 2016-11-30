package com.gruuf.web;

import com.google.appengine.api.utils.SystemProperty;
import com.gruuf.GruufConstants;
import com.gruuf.model.Bike;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.EventType;
import com.gruuf.model.User;
import com.gruuf.services.*;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.config.ConfigurationProvider;
import com.opensymphony.xwork2.config.providers.EnvsValueSubstitutor;
import com.opensymphony.xwork2.config.providers.ValueSubstitutor;
import com.opensymphony.xwork2.inject.ContainerBuilder;
import com.opensymphony.xwork2.inject.Context;
import com.opensymphony.xwork2.inject.Factory;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.inject.Scope;
import com.opensymphony.xwork2.util.location.LocatableProperties;
import com.opensymphony.xwork2.util.location.LocationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.StrutsConstants;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.DispatcherListener;

public class GruufConfigurationProvider implements ConfigurationProvider, DispatcherListener {

    private static final Logger LOG = LogManager.getLogger(GruufConfigurationProvider.class);

    private ValueSubstitutor substitutor;

    @Inject
    public void setSubstitutor(ValueSubstitutor substitutor) {
        this.substitutor = substitutor;
    }

    @Override
    public void destroy() {
        Dispatcher.removeDispatcherListener(this);
    }

    @Override
    public void init(Configuration configuration) throws ConfigurationException {
        Dispatcher.addDispatcherListener(this);
    }

    @Override
    public boolean needsReload() {
        return false;
    }

    @Override
    public void loadPackages() throws ConfigurationException {
    }

    @Override
    public void register(ContainerBuilder containerBuilder, LocatableProperties locatableProperties) throws ConfigurationException {
        final boolean devMode = SystemProperty.environment.value() != SystemProperty.Environment.Value.Production;

        LOG.debug("Setting {} to value {}", StrutsConstants.STRUTS_DEVMODE, devMode);
        locatableProperties.setProperty(
                StrutsConstants.STRUTS_DEVMODE,
                Boolean.toString(devMode),
                LocationUtils.getLocation(this, String.format("Class %s", getClass().getSimpleName()))
        );

        locatableProperties.setProperty(
                GruufConstants.HOST_URL,
                devMode ? "http://localhost:8080" : "https://gruuf.com",
                LocationUtils.getLocation(this, String.format("Class %s", getClass().getSimpleName()))
        );

        locatableProperties.setProperty(
                GruufConstants.STORAGE_BUCKET_NAME,
                devMode ? "staging.gruuf-webapp.appspot.com" : "gruuf-webapp.appspot.com",
                LocationUtils.getLocation(this, String.format("Class %s", getClass().getSimpleName()))
        );

        locatableProperties.setProperty(
                GruufConstants.STORAGE_ROOT_URL,
                "https://storage.googleapis.com/",
                LocationUtils.getLocation(this, String.format("Class %s", getClass().getSimpleName()))
        );

        locatableProperties.setProperty(
                GruufConstants.STORAGE_TOTAL_ALLOWED_SPACE,
                String.valueOf(1024 * 1024 * 20), // 20 MB
                LocationUtils.getLocation(this, String.format("Class %s", getClass().getSimpleName()))
        );

        locatableProperties.setProperty(
                GruufConstants.OAUTH_GOOGLE_API_KEY,
                substitutor.substitute("${env.OAUTH_GOOGLE_API_KEY}"),
                LocationUtils.getLocation(this, String.format("Class %s", getClass().getSimpleName()))
        );

        locatableProperties.setProperty(
                GruufConstants.OAUTH_GOOGLE_API_SECRET,
                substitutor.substitute("${env.OAUTH_GOOGLE_API_SECRET}"),
                LocationUtils.getLocation(this, String.format("Class %s", getClass().getSimpleName()))
        );

        containerBuilder.factory(UserStore.class, new Factory<UserStore>() {
            @Override
            public UserStore create(Context context) throws Exception {
                return new UserStore(User.class);
            }
        }, Scope.SINGLETON);

        containerBuilder.factory(BikeHistory.class, new Factory<BikeHistory>() {
            @Override
            public BikeHistory create(Context context) throws Exception {
                return new BikeHistory(BikeEvent.class);
            }
        }, Scope.SINGLETON);

        containerBuilder.factory(Garage.class, new Factory<Garage>() {
            @Override
            public Garage create(Context context) throws Exception {
                return new Garage(Bike.class);
            }
        }, Scope.SINGLETON);

        containerBuilder.factory(MailBox.class, new Factory<MailBox>() {
            @Override
            public MailBox create(Context context) throws Exception {
                MailBox mailBox = new MailBox();
                context.getContainer().inject(mailBox);
                return mailBox;
            }
        }, Scope.SINGLETON);

        containerBuilder.factory(EventTypes.class, new Factory<EventTypes>() {
            @Override
            public EventTypes create(Context context) throws Exception {
                return new EventTypes(EventType.class);
            }
        }, Scope.SINGLETON);

        containerBuilder.factory(AttachmentsStorage.class, new Factory<AttachmentsStorage>() {
            @Override
            public AttachmentsStorage create(Context context) throws Exception {
                return context.getContainer().inject(AttachmentsStorage.class);
            }
        }, Scope.SINGLETON);
    }

    @Override
    public void dispatcherInitialized(Dispatcher du) {
    }

    @Override
    public void dispatcherDestroyed(Dispatcher du) {

    }
}
