package com.epam.aa.sportfinder.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

public class Company extends BaseEntity {
    @NotBlank(message = "company name cannot be blank")
    private String name;
    @Valid
    private Address address;

    public Company() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}