package com.epam.aa.sportfinder.model;

import java.util.ArrayList;
import java.util.List;

public class ContactInfo extends BaseEntity {
    private String email;
    private Address address;
    private List<String> phoneNumbers;

    public void setEmail(String email) {
        this.email = email;
    }

    public void addPhoneNumber(String phoneNumber) {
        if (phoneNumbers == null) phoneNumbers = new ArrayList<String>();
        phoneNumbers.add(phoneNumber);
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }
}