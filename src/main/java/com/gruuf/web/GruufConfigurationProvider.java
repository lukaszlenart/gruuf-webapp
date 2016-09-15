package com.gruuf.web;

import com.google.appengine.api.utils.SystemProperty;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.config.ConfigurationProvider;
import com.opensymphony.xwork2.inject.ContainerBuilder;
import com.opensymphony.xwork2.util.location.LocatableProperties;
import com.opensymphony.xwork2.util.location.LocationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.StrutsConstants;

public class GruufConfigurationProvider implements ConfigurationProvider {

    private static final Logger LOG = LogManager.getLogger(GruufConfigurationProvider.class);

    @Override
    public void destroy() {
    }

    @Override
    public void init(Configuration configuration) throws ConfigurationException {
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
}