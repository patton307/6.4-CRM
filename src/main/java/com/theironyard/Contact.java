package com.theironyard;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * Created by landonkail on 11/12/15.
 */
@Entity
public class Contact {
    @Id
    @GeneratedValue
    Integer id;

    String firstName;
    String lastName;

    String email;

    String phone;

    String address;
    String city;
    String state;
    String zip;

    String company;
    String jobTitle;


    String firstContact;
    String lastContact;

    String notes;

    @ManyToOne
    User user;


}
