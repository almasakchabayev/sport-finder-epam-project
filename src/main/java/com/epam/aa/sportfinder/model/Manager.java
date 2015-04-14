package com.epam.aa.sportfinder.model;

import java.util.ArrayList;
import java.util.List;

public class Manager extends User {
    private Company company;
    private List<String> phonetNumbers;
    private List<SportPlace> sportPlaces;

    public Manager() {
        this.phonetNumbers = new ArrayList<>();
        this.sportPlaces = new ArrayList<>();
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<String> getPhonetNumbers() {
        return phonetNumbers;
    }

    public void setPhonetNumbers(List<String> phonetNumbers) {
        this.phonetNumbers = phonetNumbers;
    }

    public List<SportPlace> getSportPlaces() {
        return sportPlaces;
    }

    public void setSportPlaces(List<SportPlace> sportPlaces) {
        this.sportPlaces = sportPlaces;
    }
}