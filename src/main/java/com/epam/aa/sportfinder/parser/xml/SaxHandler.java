package com.epam.aa.sportfinder.parser.xml;

import com.epam.aa.sportfinder.model.Address;
import com.epam.aa.sportfinder.model.ContactInfo;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Stack;

public class SaxHandler extends DefaultHandler {
    private ContactInfo contactInfo= new ContactInfo();
    //Deque
    private Stack<String> elementStack = new Stack<>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        elementStack.push(qName);
        if ("address".equals(qName)) {
            contactInfo.setAddress(new Address());
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        elementStack.pop();
    }

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException {
        String value = new String(chars, start, length);
        if (value.length() == 0) return;

        switch (currentElement()) {
            case "id": contactInfo.setId(Long.valueOf(value)); break;
            case "deleted": contactInfo.setDeleted(Boolean.valueOf(value)); break;
            case "email": contactInfo.setEmail(value); break;
            case "country": contactInfo.getAddress().setCountry(value); break;
            case "city": contactInfo.getAddress().setCity(value); break;
            case "addressLine1" : contactInfo.getAddress().setAddressLine1(value); break;
            case "addressLine2" : contactInfo.getAddress().setAddressLine2(value); break;
            case "zipCode": contactInfo.getAddress().setZipCode(value); break;
            case "phoneNumber": contactInfo.addPhoneNumber(value); break;
        }
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    private String currentElement() {
        return this.elementStack.peek();
    }
}
