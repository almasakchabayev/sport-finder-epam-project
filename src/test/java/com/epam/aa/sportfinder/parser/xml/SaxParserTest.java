package com.epam.aa.sportfinder.parser.xml;

import com.epam.aa.sportfinder.model.ContactInfo;
import com.epam.aa.sportfinder.parser.Parser;
import org.junit.Test;


import static org.junit.Assert.*;

public class SaxParserTest {

    @Test
    public void testParse() throws Exception {
        Parser parser = new SaxParser();
        ContactInfo contactInfo = parser.parse("xml/contact-info.xml");
        assertTrue(contactInfo.getId().equals(Long.valueOf(1)));
        assertTrue(contactInfo.getAddress().getCountry().equals("Kazakhstan"));
    }
}