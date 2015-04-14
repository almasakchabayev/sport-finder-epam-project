//package com.epam.aa.sportfinder.parser.xml;
//
//import com.epam.aa.sportfinder.model.ContactInfo;
//import com.epam.aa.sportfinder.parser.Parser;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import org.xml.sax.SAXException;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import java.io.IOException;
//import java.io.InputStream;
//
//public class DomParser implements Parser {
//
//    @Override
//    public ContactInfo parse(InputStream inputStream) {
//        ContactInfo contactInfo = new ContactInfo();
//
//        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
//        try {
//            DocumentBuilder builder = builderFactory.newDocumentBuilder();
//            Document document = builder.parse(inputStream);
//
//            document.getDocumentElement().normalize();
//
//            Element rootElement = document.getDocumentElement();
//            NodeList nodes = rootElement.getChildNodes();
//
//
//            for (int i = 0; i < nodes.getLength(); i++) {
//                Node node = nodes.item(i);
//
//                if (node instanceof Element) {
//                    Element element = (Element) node;
//                    if (element.getTagName().equals("id")) {
//                        contactInfo.setId(Long.valueOf(element.getTextContent()));
//                    }
//                }
//            }
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return contactInfo;
//    }
//}
