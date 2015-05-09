package com.epam.aa.sportfinder.model;

import org.hibernate.validator.constraints.NotBlank;

public class FloorCoverage extends BaseEntity {
    @NotBlank(message = "please specify floor coverage")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) name = "";
        this.name = name.toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FloorCoverage that = (FloorCoverage) o;

        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
