package com.sam.teamd.samandroidclient.service;

import com.sam.teamd.samandroidclient.model.Login;
import com.sam.teamd.samandroidclient.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by david on 8/10/17.
 */

public interface UserClient {

    @POST("login")
    Call<User> login (@Body Login login);

}
