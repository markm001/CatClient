package com.ccat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Parses the application.properties file provided in the resources.
 * TODO: Change this to a non-static class -> .properties file name should be provided by User
 */
public class PropertiesParser {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesParser.class);

    private static final Properties PROPERTIES = new Properties();
    private static PropertiesParser INSTANCE;

    private PropertiesParser() {
        String propertiesFileName = "application.properties";
        try(InputStream input =
                    PropertiesParser.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
            PROPERTIES.load(input);
        } catch (IOException e) {
            logger.warn("Unable to load properties file '{}': {} - {}",
                    propertiesFileName, e.getClass(), e.getMessage());
            throw new RuntimeException("Unable to load properties file.");
        }
    }

    public String getProperty(String property) {
        return PROPERTIES.getProperty(property);
    }

    public static PropertiesParser getInstance() {
        if(INSTANCE == null) INSTANCE = new PropertiesParser();

        return INSTANCE;
    }
}
