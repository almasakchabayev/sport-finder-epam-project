package com.epam.aa.sportfinder.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

public class Manager extends User {
    @Valid
    private Company company;
    @NotEmpty(message = "Please specify at least one phone number")
    @Valid
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

    @Transient
    public List<SportPlace> getSportPlaces() {
        return sportPlaces;
    }

    public void setSportPlaces(List<SportPlace> sportPlaces) {
        this.sportPlaces = sportPlaces;
    }

    @Transient
    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public boolean containsPhoneNumber(PhoneNumber phoneNumber) {
        return phoneNumbers.contains(phoneNumber);
    }

    public void removePhoneNumber(PhoneNumber phoneNumber) {
        phoneNumbers.remove(phoneNumber);
    }

    public void addPhoneNumber(PhoneNumber phoneNumber) {
        phoneNumbers.add(phoneNumber);
    }
}