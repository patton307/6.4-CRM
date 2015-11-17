package com.theironyard.entities;

import javax.persistence.*;

/**
 * Created by landonkail on 11/12/15.
 */
@Entity
public class Contact {
    @Id
    @GeneratedValue
    Integer id;

    @Column(nullable = false)
    public String firstName;

    @Column(nullable = false)
    public String lastName;

    @Column(nullable = false)
    public String email;

    public String phone;

    public String address;

    public String city;

    public String state;
    public String zip;

    public String company;
    public String jobTitle;


    public String firstContact;
    public String lastContact;

    public String notes;

    @ManyToOne
    public User user;


}
