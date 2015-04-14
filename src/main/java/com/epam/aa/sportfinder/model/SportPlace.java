package com.epam.aa.sportfinder.model;

import java.time.ZonedDateTime;
import java.util.List;

public class SportPlace extends BaseEntity {
    //features
    private String size;
    private Integer capacity;
    //TODO: make fixed values;
    private String floorCoverage;
    private boolean indoor;

    //Infrastructure
    private boolean changingRoom;
    private boolean shower;
    private boolean toilet;
    private boolean lightening;
    private Integer tribuneCapacity;
    private String otherInfrastructureFeatures;

    private double pricePerHour;

    private String description;
    private Address address;
    //TODO: fixed values like sport coverage
    private List<String> sports;

    private ZonedDateTime openFrom;
    private ZonedDateTime openTo;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getFloorCoverage() {
        return floorCoverage;
    }

    public void setFloorCoverage(String floorCoverage) {
        this.floorCoverage = floorCoverage;
    }

    public boolean isIndoor() {
        return indoor;
    }

    public void setIndoor(boolean indoor) {
        this.indoor = indoor;
    }

    public boolean isChangingRoom() {
        return changingRoom;
    }

    public void setChangingRoom(boolean changingRoom) {
        this.changingRoom = changingRoom;
    }

    public boolean isShower() {
        return shower;
    }

    public void setShower(boolean shower) {
        this.shower = shower;
    }

    public boolean isToilet() {
        return toilet;
    }

    public void setToilet(boolean toilet) {
        this.toilet = toilet;
    }

    public boolean isLightening() {
        return lightening;
    }

    public void setLightening(boolean lightening) {
        this.lightening = lightening;
    }

    public Integer getTribuneCapacity() {
        return tribuneCapacity;
    }

    public void setTribuneCapacity(Integer tribuneCapacity) {
        this.tribuneCapacity = tribuneCapacity;
    }

    public String getOtherInfrastructureFeatures() {
        return otherInfrastructureFeatures;
    }

    public void setOtherInfrastructureFeatures(String otherInfrastructureFeatures) {
        this.otherInfrastructureFeatures = otherInfrastructureFeatures;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<String> getSports() {
        return sports;
    }

    public void setSports(List<String> sports) {
        this.sports = sports;
    }

    public ZonedDateTime getOpenFrom() {
        return openFrom;
    }

    public void setOpenFrom(ZonedDateTime openFrom) {
        this.openFrom = openFrom;
    }

    public ZonedDateTime getOpenTo() {
        return openTo;
    }

    public void setOpenTo(ZonedDateTime openTo) {
        this.openTo = openTo;
    }
}