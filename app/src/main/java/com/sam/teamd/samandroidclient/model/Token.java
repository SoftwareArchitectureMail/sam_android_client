package com.sam.teamd.samandroidclient.model;

/**
 * Created by david on 8/10/17.
 */

public class Token {

    private String token;
    private String refToken;

    public Token(String token, String refToken) {
        this.token = token;
        this.refToken = refToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefToken() {
        return refToken;
    }

    public void setRefToken(String refToken) {
        this.refToken = refToken;
    }
}
