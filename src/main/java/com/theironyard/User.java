package com.theironyard;

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

    String username;
    String password;
    String email;

    String firstName;
    String lastName;

    @OneToMany(mappedBy = "user")
    List<Contact> contacts;

}
