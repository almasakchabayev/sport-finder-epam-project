package com.epam.aa.sportfinder.model;

public class FloorCoverage extends BaseEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) name = "";
        this.name = name.toLowerCase();
    }
}
