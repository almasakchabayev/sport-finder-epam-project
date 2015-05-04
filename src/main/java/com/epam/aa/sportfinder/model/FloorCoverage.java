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
}
