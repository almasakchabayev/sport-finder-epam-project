package com.epam.aa.sportfinder.model;

import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class Sport extends BaseEntity {
    @NotBlank(message = "please specify sport")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sport sport = (Sport) o;

        return !(name != null ? !name.equals(sport.name) : sport.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
