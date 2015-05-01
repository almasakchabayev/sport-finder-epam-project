package com.epam.aa.sportfinder.model;

import org.hibernate.validator.constraints.NotBlank;

public class PhoneNumber extends BaseEntity {
    @NotBlank(message = "phone number cannot be empty")
    private String number;

    public PhoneNumber() {
    }

    public PhoneNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
