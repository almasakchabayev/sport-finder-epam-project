package com.epam.aa.sportfinder.model;

import org.hibernate.validator.constraints.NotBlank;

public class Address extends BaseEntity {
    @NotBlank(message = "country name cannot be blank")
    private String country;
    @NotBlank(message = "city cannot be blank")
    private String city;
    @NotBlank(message = "address line 1 cannot be blank")
    private String addressLine1;
    @NotBlank(message = "address line 2 cannot be blank")
    private String addressLine2;
    @NotBlank(message = "zipcode cannot be blank")
    private String zipcode;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}