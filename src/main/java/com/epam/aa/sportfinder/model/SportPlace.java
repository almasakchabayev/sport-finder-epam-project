package com.epam.aa.sportfinder.model;

import java.time.ZonedDateTime;
import java.util.List;

public class SportPlace extends BaseEntity {
    //features
    private String size;
    private Integer capacity;
    //TODO: make fixed values;
    private String floorCoverage;
    private boolean indoor;

    //Infrastructure
    private boolean changingRoom;
    private boolean shower;
    private boolean toilet;
    private boolean lightening;
    private Integer tribuneCapacity;
    private String otherInfrastructureFeatures;

    private double pricePerHour;

    private String description;
    private Address address;
    //TODO: fixed values like sport coverage
    private List<String> sports;

    private ZonedDateTime openFrom;
    private ZonedDateTime openTo;
}