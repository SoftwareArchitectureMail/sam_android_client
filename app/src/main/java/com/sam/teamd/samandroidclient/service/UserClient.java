package com.sam.teamd.samandroidclient.service;

import com.sam.teamd.samandroidclient.model.Login;
import com.sam.teamd.samandroidclient.model.Token;
import com.sam.teamd.samandroidclient.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by david on 8/10/17.
 */

public interface UserClient {

    @POST("users/login")
    Call<User> login (@Body Login login);

    @GET("user")
    Call<User> getCurrentUser (@Header("Authorization") String authToken);

    @GET("refreshtoken")
    Call<Token> refreshToken (@Header("Authorization") String refToken);

    @GET("users/check/{username}")
    Call<ResponseBody> validateUsername (@Path("username") String username);

    @POST("users/create")
    Call<User> createUser (@Body User user);

}
