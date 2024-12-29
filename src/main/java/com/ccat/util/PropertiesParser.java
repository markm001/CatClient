package com.ccat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Parses the specified .properties file provided in the resources.
 */
public class PropertiesParser {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesParser.class);

    private static final Map<String,PropertiesParser> parserInstance = new HashMap<>();
    private final Properties PROPERTIES = new Properties();

    private PropertiesParser(String propertiesFileName) {
        try(InputStream input =
                    PropertiesParser.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
            PROPERTIES.load(input);
        } catch (IOException e) {
            logger.warn("Unable to load properties file '{}': {} - {}",
                    propertiesFileName, e.getClass(), e.getMessage());
            throw new RuntimeException("Unable to load properties file.");
        }
    }

    /**
     * Returns the property if found within the Parser Instance
     * @param property Key value
     * @return Property associated with the specified key
     */
    public String getProperty(String property) {
        return PROPERTIES.getProperty(property);
    }

    /**
     * Returns the Parser Instance associated with the specified filename
     * @param propertiesFileName filename of the properties file
     * @return Parser Instance containing the parsed properties
     */
    public static PropertiesParser getInstance(String propertiesFileName) {
        parserInstance.putIfAbsent(propertiesFileName, new PropertiesParser(propertiesFileName));

        return parserInstance.get(propertiesFileName);
    }
}
