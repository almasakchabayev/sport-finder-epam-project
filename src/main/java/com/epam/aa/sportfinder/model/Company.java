package com.epam.aa.sportfinder.model;

import java.util.ArrayList;
import java.util.List;

public class Company extends BaseEntity {
    private String name;
    private Address address;
    private List<String> phoneNumbers;
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

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
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
}