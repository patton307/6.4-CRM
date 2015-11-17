package com.theironyard.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by landonkail on 11/12/15.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    Integer id;

    @Column(nullable = false)
    public String username;

    @Column(nullable = false)
    public String password;

    @Column(nullable = false)
    public String email;

    @Column(nullable = false)
    public String firstName;

    @Column(nullable = false)
    public String lastName;

    @OneToMany(mappedBy = "user")
    List<Contact> contacts;

}
