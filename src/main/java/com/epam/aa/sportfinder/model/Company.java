package com.epam.aa.sportfinder.model;

import java.util.ArrayList;
import java.util.List;

public class Company extends BaseEntity {
    private String name;
    private Address address;
    private List<PhoneNumber> phoneNumbers;
    private List<SportPlace> sportPlaces;
    private List<Manager> managers;

    public Company() {
        this.phoneNumbers = new ArrayList<>();
        this.sportPlaces = new ArrayList<>();
        this.managers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    public List<SportPlace> getSportPlaces() {
        return sportPlaces;
    }

    public void setSportPlaces(List<SportPlace> sportPlaces) {
        this.sportPlaces = sportPlaces;
    }

    public List<Manager> getManagers() {
        return managers;
    }

    public void setManagers(List<Manager> managers) {
        this.managers = managers;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}