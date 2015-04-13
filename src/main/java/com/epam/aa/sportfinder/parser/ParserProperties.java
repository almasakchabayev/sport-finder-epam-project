package com.epam.aa.sportfinder.parser;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ParserProperties {
    private static final Logger logger = LoggerFactory.getLogger(ParserProperties.class);

    private static final String PARSER_PROPERTIES_FILE = "/parser.properties";
    private static final Properties PARSER_PROPERTIES = new Properties();

    public static void loadProperties() {
        InputStream in = ParserProperties.class.getResourceAsStream(PARSER_PROPERTIES_FILE);
        if (in == null) {
            throw new ParserException(
                    "ParserProperties file '" + PARSER_PROPERTIES_FILE + "' is missing in classpath.");
        }
        try {
            PARSER_PROPERTIES.load(in);
            in.close();
            logger.debug("Properties from '{}' has loaded successfully", PARSER_PROPERTIES_FILE);
        } catch (IOException e) {
            throw new ParserException(
                    "Cannot load properties file '" + PARSER_PROPERTIES_FILE + "'.", e);
        }
    }

    public static String getProperty(String key) {
        loadProperties();
        String property = PARSER_PROPERTIES.getProperty(key);
        if (property == null || property.trim().length() == 0) {
            throw new ParserException(
                    "Required property '" + key + "'"
                            + " is missing in properties file '" + PARSER_PROPERTIES + "'.");
        }
        return property;
    }


}
