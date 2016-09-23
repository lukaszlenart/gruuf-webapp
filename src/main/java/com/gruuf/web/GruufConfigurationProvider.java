package com.gruuf.web;

import com.google.appengine.api.utils.SystemProperty;
import com.gruuf.services.BikeHistory;
import com.gruuf.services.Garage;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.config.ConfigurationProvider;
import com.opensymphony.xwork2.inject.ContainerBuilder;
import com.opensymphony.xwork2.util.location.LocatableProperties;
import com.opensymphony.xwork2.util.location.LocationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.StrutsConstants;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.DispatcherListener;

public class GruufConfigurationProvider implements ConfigurationProvider, DispatcherListener {

    private static final Logger LOG = LogManager.getLogger(GruufConfigurationProvider.class);

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
    }

    @Override
    public void dispatcherInitialized(Dispatcher du) {
        Garage garage = du.getContainer().getInstance(Garage.class);
        if (garage != null) {
            LOG.debug("Reindexing Garage");
            garage.reindex();
        }
        BikeHistory bikeHistory = du.getContainer().getInstance(BikeHistory.class);
        if (bikeHistory != null) {
            LOG.debug("Reindexing BikeHistory");
            bikeHistory.reindex();
        }
    }

    @Override
    public void dispatcherDestroyed(Dispatcher du) {

    }
}
