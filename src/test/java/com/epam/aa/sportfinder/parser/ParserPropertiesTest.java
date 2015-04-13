package com.epam.aa.sportfinder.parser;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParserPropertiesTest {

    @Test
    public void testGetProperty() throws Exception {
        String dataType = ParserProperties.getProperty("dataFormat");
        assertEquals("xml", dataType);
    }
}