package com.epam.aa.sportfinder.model;


import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDate;

public abstract class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private ContactInfo contactInfo; // email, address, contacts
    //TODO: what to do about password?
    private String password;
    private LocalDate birthDate;
}
