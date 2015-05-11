package com.epam.aa.sportfinder.model;

public class Image extends BaseEntity {
    private byte[] imageArray;

    public byte[] getImageArray() {
        return imageArray;
    }

    public void setImageArray(byte[] imageArray) {
        this.imageArray = imageArray;
    }
}
