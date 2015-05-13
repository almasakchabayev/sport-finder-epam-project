package com.epam.aa.sportfinder.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.beans.Transient;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class SportPlace extends BaseEntity {
    //features
    @NotBlank(message = "please specify size")
    private String size;
    @NotNull(message = "please specify capacity")
    private Integer capacity;
    private FloorCoverage floorCoverage;
    private boolean indoor;

    //Infrastructure
    private boolean changingRoom;
    private boolean shower;
    private boolean lightening;
    private Integer tribuneCapacity;
    private String otherInfrastructureFeatures;

    @NotNull(message = "please specify price")
    private BigDecimal pricePerHour;

    private String description;
    @Valid
    private Address address;
    @NotEmpty(message = "Please specify at least one sport")
    private List<Sport> sports;

    @NotEmpty(message = "Please add at least one image")
    private List<Image> images;

    @NotNull(message = "only managers can create sport places")
    private Manager manager;

    public SportPlace() {
        this.sports = new ArrayList<>();
        this.images = new ArrayList<>();
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

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @Transient
    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void addImage(Image image) {
        images.add(image);
    }
}