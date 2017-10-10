package com.sam.teamd.samandroidclient.model;

import android.util.Log;

import com.sam.teamd.samandroidclient.service.Api;
import com.sam.teamd.samandroidclient.service.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by david on 8/10/17.
 */

public class User {

    private static final String LOG_TAG = User.class.getSimpleName();

    private String firstName;
    private String lastName;
    private String username;
    private String gender;
    private String dateBirth;
    private String mobilePhone;
    private Token token;

    public User(String firstName, String lastName, String username, String gender, String dateBirth,
                String mobilePhone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.gender = gender;
        this.dateBirth = dateBirth;
        this.mobilePhone = mobilePhone;
    }

    public User(String firstName, String lastName, String username, String gender, String dateBirth,
                String mobilePhone, Token token) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.gender = gender;
        this.dateBirth = dateBirth;
        this.mobilePhone = mobilePhone;
        this.token = token;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
