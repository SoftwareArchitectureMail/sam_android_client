package com.sam.teamd.samandroidclient.model;

/**
 * Created by david on 8/10/17.
 */

public class Token {

    private String token;
    private String refresh;

    public Token() {
    }

    public Token(String token, String refresh) {
        this.token = token;
        this.refresh = refresh;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }
}
