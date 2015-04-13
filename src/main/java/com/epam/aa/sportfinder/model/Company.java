package com.epam.aa.sportfinder.model;

import java.util.List;

public class Company extends BaseEntity {
    private String name;
    private Address address;
    private List<String> phoneNumbers;
    private List<SportPlace> sportPlaces;
    private List<Manager> managers;
}