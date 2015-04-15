package com.epam.aa.sportfinder.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class SportPlace extends BaseEntity {
    //features
    private String size;
    private Integer capacity;
    private FloorCoverage floorCoverage;
    private boolean indoor;

    //Infrastructure
    private boolean changingRoom;
    private boolean shower;
    private boolean lightening;
    private Integer tribuneCapacity;
    private String otherInfrastructureFeatures;

    private BigDecimal pricePerHour;

    private String description;
    private Address address;
    private List<Sport> sports;

    public SportPlace() {
        this.sports = new ArrayList<>();
    }


}