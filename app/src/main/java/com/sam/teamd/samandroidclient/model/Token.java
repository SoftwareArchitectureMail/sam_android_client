package com.sam.teamd.samandroidclient.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by david on 8/10/17.
 */

public class Token implements Parcelable{

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token);
        dest.writeString(this.refresh);
    }

    protected Token(Parcel in) {
        this.token = in.readString();
        this.refresh = in.readString();
    }

    public static final Creator<Token> CREATOR = new Creator<Token>() {
        @Override
        public Token createFromParcel(Parcel source) {
            return new Token(source);
        }

        @Override
        public Token[] newArray(int size) {
            return new Token[size];
        }
    };
}
