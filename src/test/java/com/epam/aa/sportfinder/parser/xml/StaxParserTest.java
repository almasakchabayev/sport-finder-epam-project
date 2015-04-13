package com.epam.aa.sportfinder.parser.xml;

import com.epam.aa.sportfinder.model.ContactInfo;
import com.epam.aa.sportfinder.parser.Parser;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class StaxParserTest {

    @Test
    public void testParse() throws Exception {
        Parser parser = new StaxParser();
        ContactInfo contactInfo = parser.parse("xml/contact-info.xml");
        assertTrue(contactInfo.getId().equals(Long.valueOf(1)));
    }

    @Test
    public void testWrite() {
        StaxParser parser = new StaxParser();
        parser.write();
        ClassLoader classLoader = getClass().getClassLoader();
        assertNotNull(classLoader.getResource("xml/output.xml").getFile());
    }
}