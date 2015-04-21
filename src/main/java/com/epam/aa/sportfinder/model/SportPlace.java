package com.epam.aa.sportfinder.model;

import java.beans.Transient;
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

//    @Transient
    public FloorCoverage getFloorCoverage() {
        return floorCoverage;
    }

    public void setFloorCoverage(FloorCoverage floorCoverage) {
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

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    @Transient
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Transient
    public List<Sport> getSports() {
        return sports;
    }

    public void setSports(List<Sport> sports) {
        this.sports = sports;
    }

    public boolean containsSport(Sport sport) {
        return sports.contains(sport);
    }

    public void removeSport(Sport sport) {
        sports.remove(sport);
    }

    public void addSport(Sport sport) {
        sports.add(sport);
    }
}