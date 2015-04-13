package com.epam.aa.sportfinder.model;

import java.time.LocalDateTime;
import java.util.List;

public class SportPlace extends BaseEntity {
    private String size;
    private boolean indoor; // if false outdoor
    private String floorCoverage;
    private int capacity;
    private boolean changingRoom;
    private boolean bathRoom;
    private boolean lighting;
    private String otherInfrastructureFeatures;
    private String description;
    private double pricePerHour;
    private Address address; // if a company has sport places in different locations
    private SportPlacesAdmin admin;
    private List<Sport> sports;
}