//package com.epam.aa.sportfinder.parser.xml;
//
//import com.epam.aa.sportfinder.model.ContactInfo;
//import com.epam.aa.sportfinder.parser.Parser;
//import com.epam.aa.sportfinder.parser.ParserException;
//import org.xml.sax.SAXException;
//import org.xml.sax.helpers.DefaultHandler;
//
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.parsers.SAXParser;
//import javax.xml.parsers.SAXParserFactory;
//import java.io.IOException;
//import java.io.InputStream;
//
//public class SaxParser implements Parser {
//
//    @Override
//    public ContactInfo parse(InputStream inputStream) {
//        SAXParserFactory factory = SAXParserFactory.newInstance();
//        try {
//            SAXParser parser = factory.newSAXParser();
//            SaxHandler handler = new SaxHandler();
//            parser.parse(inputStream, handler);
//            return handler.getContactInfo();
//        } catch (ParserConfigurationException e) {
//            throw new ParserException(e);
//        } catch (SAXException e) {
//            throw new ParserException(e);
//        } catch (IOException e) {
//            throw new ParserException(e);
//        }
//    }
//}
