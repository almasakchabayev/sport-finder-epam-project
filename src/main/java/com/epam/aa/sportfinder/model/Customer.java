package com.epam.aa.sportfinder.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Customer extends User {
    private List<SportPlace> favourites;
}
