package com.epam.aa.sportfinder.parser.xml;

import com.epam.aa.sportfinder.model.ContactInfo;
import com.epam.aa.sportfinder.parser.Parser;
import com.epam.aa.sportfinder.parser.ParserException;

import javax.xml.stream.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class StaxParser implements Parser {

    @Override
    public ContactInfo parse(InputStream inputStream) {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        try {
            XMLStreamReader streamReader = factory.createXMLStreamReader(inputStream);
            while(streamReader.hasNext()) {
                int next = streamReader.next();
                if (streamReader.getEventType() == XMLStreamReader.START_ELEMENT){
                    String elementName = streamReader.getLocalName();
                    if ("contactInfo".equals(elementName)){
                        return parseContactInfo(streamReader);
                    }
                }
            }
            return null;
        } catch (XMLStreamException e) {
            throw new ParserException(e);
        }

    }

    private ContactInfo parseContactInfo(XMLStreamReader streamReader) {
        ContactInfo contactInfo = new ContactInfo();
        try {
            while(streamReader.hasNext()) {
                int next = streamReader.next();
                if (streamReader.getEventType() == XMLStreamReader.END_ELEMENT){
                    String elementName = streamReader.getLocalName();
                    if ("contactInfo".equals(elementName)){
                        return contactInfo;
                    }
                } else if (streamReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    String elementName = streamReader.getLocalName();
                    if ("id".equals(elementName)){
                        streamReader.next();
                        if (streamReader.getEventType() == XMLStreamReader.CHARACTERS) {
                            String value = new String(
                                    streamReader.getTextCharacters(),
                                    streamReader.getTextStart(),
                                    streamReader.getTextLength());
                            contactInfo.setId(Long.valueOf(value));
                        }
                    }
                    continue;
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return contactInfo;
    }

    public void write() {
        XMLOutputFactory factory      = XMLOutputFactory.newInstance();

        try {
            XMLStreamWriter writer = factory.createXMLStreamWriter(
                    new FileWriter("src/main/resources/xml/output.xml"));

            writer.writeStartDocument();
            writer.writeStartElement("document");
            writer.writeStartElement("data");
            writer.writeAttribute("name", "value");
            writer.writeEndElement();
            writer.writeEndElement();
            writer.writeEndDocument();

            writer.flush();
            writer.close();

        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
