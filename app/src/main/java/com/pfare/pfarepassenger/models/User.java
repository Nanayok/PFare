package com.pfare.pfarepassenger.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by macbookpro on 10/21/20.
 */

@IgnoreExtraProperties
public class User {

    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String email;
    public String password;

    public User(){

    }

    public User(String firstName, String lastName, String phoneNumber, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }
}
