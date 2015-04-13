package com.epam.aa.sportfinder.model;


import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDate;
import java.util.List;

public abstract class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}