package com.sam.teamd.samandroidclient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by david on 8/10/17.
 */

public class User implements Serializable{

    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private String username;
    private String password;
    @SerializedName("current_email")
    private String currentEmail;

    @SerializedName("date_birth")
    private Date dateBirth;

    @SerializedName("mobile_phone")
    private long mobilePhone;

    @Expose(serialize = false)
    private int location;
    private int gender;

    private Token token;

    public User(String firstName, String lastName, Date dateBirth, long mobilePhone, int gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.mobilePhone = mobilePhone;
        this.gender = gender;
    }

    public User(String firstName, String lastName, String username, String currentEmail, Date dateBirth, long mobilePhone, int location, int gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.currentEmail = currentEmail;
        this.dateBirth = dateBirth;
        this.mobilePhone = mobilePhone;
        this.location = location;
        this.gender = gender;
    }

    public User(String firstName, String lastName, String username, String currentEmail, Date dateBirth, long mobilePhone, int location, int gender, Token token) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.currentEmail = currentEmail;
        this.dateBirth = dateBirth;
        this.mobilePhone = mobilePhone;
        this.location = location;
        this.gender = gender;
        this.token = token;
    }



    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrentEmail() {
        return currentEmail;
    }

    public void setCurrentEmail(String currentEmail) {
        this.currentEmail = currentEmail;
    }

    public long getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(long mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }


}
