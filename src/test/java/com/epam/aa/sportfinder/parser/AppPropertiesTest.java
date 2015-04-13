package com.epam.aa.sportfinder.parser;

import com.epam.aa.sportfinder.config.AppProperties;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppPropertiesTest {

    @Test
    public void testGetProperty() throws Exception {
        String dataType = AppProperties.getParserProperty("dataFormat");
        assertEquals("xml", dataType);
    }
}