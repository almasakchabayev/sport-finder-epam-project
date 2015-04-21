package com.epam.aa.sportfinder.model;

import java.util.ArrayList;
import java.util.List;

public class Manager extends User {
    private Company company;
    private List<PhoneNumber> phoneNumbers;
    private List<SportPlace> sportPlaces;

    public Manager() {
        this.phoneNumbers = new ArrayList<>();
        this.sportPlaces = new ArrayList<>();
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<SportPlace> getSportPlaces() {
        return sportPlaces;
    }

    public void setSportPlaces(List<SportPlace> sportPlaces) {
        this.sportPlaces = sportPlaces;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}