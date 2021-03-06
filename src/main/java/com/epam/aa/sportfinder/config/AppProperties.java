package com.epam.aa.sportfinder.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class AppProperties {
    private static final Logger logger = LoggerFactory.getLogger(AppProperties.class);

    private static final String PARSER_PROPERTIES_FILE = "/parser.properties";
    private static final Properties PARSER_PROPERTIES = loadParserProperties();
    private static final String DAO_PROPERTIES_FILE = "/dao.properties";
    private static final Properties DAO_PROPERTIES = loadDaoProperties();
    private static final String HIKARI_PROPERTIES_FILE = "/hikari.properties";
    public static final String HIKARI_PROPERTIES_PATH = getHikariPropertiesPath();
    private static final String FLYWAY_PROPERTIES_FILE = "/flyway.properties";
    private static final Properties FLYWAY_PROPERTIES = loadFlywayProperties();

    private static Properties loadFlywayProperties() {
        InputStream in = AppProperties.class.getResourceAsStream(FLYWAY_PROPERTIES_FILE);
        if (in == null) {
            throw new ConfigurationException(
                    "DaoProperties file '" + FLYWAY_PROPERTIES_FILE + "' is missing in classpath.");
        }
        Properties result;
        try {
            result = new Properties();
            result.load(in);
            in.close();
        } catch (IOException e) {
            throw new ConfigurationException(
                    "Cannot load properties file '" + FLYWAY_PROPERTIES_FILE + "'.", e);
        }
        return result;
    }

    private static String getHikariPropertiesPath() {
        URL resource = AppProperties.class.getResource(HIKARI_PROPERTIES_FILE);
        if (resource == null) {
            throw new ConfigurationException(
                    "HikariProperties file '" + HIKARI_PROPERTIES_FILE + "' is missing in classpath.");
        }
        return resource.getPath();
    }

    private static Properties loadDaoProperties() {
        InputStream in = AppProperties.class.getResourceAsStream(DAO_PROPERTIES_FILE);
        if (in == null) {
            throw new ConfigurationException(
                    "DaoProperties file '" + DAO_PROPERTIES_FILE + "' is missing in classpath.");
        }
        Properties result;
        try {
            result = new Properties();
            result.load(in);
            in.close();
        } catch (IOException e) {
            throw new ConfigurationException(
                    "Cannot load properties file '" + DAO_PROPERTIES_FILE + "'.", e);
        }
        return result;
    }

    private static Properties loadParserProperties() {
        InputStream in = AppProperties.class.getResourceAsStream(PARSER_PROPERTIES_FILE);
        if (in == null) {
            throw new ConfigurationException(
                    "ParserProperties file '" + PARSER_PROPERTIES_FILE + "' is missing in classpath.");
        }
        Properties result;
        try {
            result = new Properties();
            result.load(in);
            in.close();
            logger.debug("Properties from '{}' has loaded successfully", PARSER_PROPERTIES_FILE);
        } catch (IOException e) {
            throw new ConfigurationException(
                    "Cannot load properties file '" + PARSER_PROPERTIES_FILE + "'.", e);
        }
        return result;
    }

    public static String getParserProperty(String key) {
        String property = PARSER_PROPERTIES.getProperty(key);
        if (property == null || property.trim().length() == 0) {
            throw new ConfigurationException(
                    "Required property '" + key + "'"
                            + " is missing in properties file '" + PARSER_PROPERTIES + "'.");
        }
        return property;
    }

    public static String getDaoProperty(String key) {
        String property = DAO_PROPERTIES.getProperty(key);
        if (property == null || property.trim().length() == 0) {
            throw new ConfigurationException(
                    "Required property '" + key + "'"
                            + " is missing in properties file '" + DAO_PROPERTIES + "'.");
        }
        return property;
    }

    public static String getFlywayProperty(String key) {
        String property = FLYWAY_PROPERTIES.getProperty(key);
        if (property == null || property.trim().length() == 0) {
            throw new ConfigurationException(
                    "Required property '" + key + "'"
                            + " is missing in properties file '" + FLYWAY_PROPERTIES + "'.");
        }
        return property;
    }

}
