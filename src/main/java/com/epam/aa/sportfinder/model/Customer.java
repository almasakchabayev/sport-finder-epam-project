package com.epam.aa.sportfinder.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Customer extends User {
    private List<SportPlace> favourites;

    public Customer() {
        this.favourites = new ArrayList<>();
    }

    public List<SportPlace> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<SportPlace> favourites) {
        this.favourites = favourites;
    }
}
