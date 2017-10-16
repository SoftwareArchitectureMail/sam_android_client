package com.sam.teamd.samandroidclient.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by david on 8/10/17.
 */

public class Login {

    private String username;
    private String password;

    @SerializedName("device_id")
    private String deviceId;

    public Login(String username, String password, String deviceId) {
        this.username = username;
        this.password = password;
        this.deviceId = deviceId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
