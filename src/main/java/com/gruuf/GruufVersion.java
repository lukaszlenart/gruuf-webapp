package com.gruuf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class GruufVersion {

    static {
        Logger logger = LogManager.getLogger(GruufVersion.class);

        try {
            Properties version = new Properties();
            version.load(GruufVersion.class.getResourceAsStream("/version.properties"));

            logger.debug("Loaded properties {}", version);


            String currentVersion = version.getProperty("version");
            logger.debug("Current version: {}", currentVersion);

            CURRENT_VERSION = currentVersion;
        } catch (IOException e) {
            logger.error("Cannot load version", e);
            CURRENT_VERSION = "unknown";
        }
    }

    public static String CURRENT_VERSION;

}
