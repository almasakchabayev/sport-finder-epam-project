package com.epam.aa.sportfinder.model;

import java.util.ArrayList;
import java.util.List;

public class Sport extends BaseEntity {
    private String name;
    private List<SportPlace> sportPlaces;

    public Sport() {
        this.sportPlaces = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) name = "";
        this.name = name.toLowerCase();
    }

    public static void main(String[] args) {
        Sport sport = new Sport();
        sport.setName(null);
    }
}
