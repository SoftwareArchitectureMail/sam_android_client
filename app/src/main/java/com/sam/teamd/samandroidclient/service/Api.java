package com.sam.teamd.samandroidclient.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.sam.teamd.samandroidclient.model.Token;
import com.sam.teamd.samandroidclient.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by david on 9/10/17.
 */

public class Api {

    private static final String LOG_TAG = Api.class.getSimpleName();


    private static Api instance = null;
    public static final String BASE_URL = "http://192.168.0.11:4000/";

    // Keep yovur services here, build them in buildRetrofit method later
    private UserClient userClient;


    public static Api getInstance() {
        if (instance == null) {
            instance = new Api();
        }

        return instance;
    }



    // Build retrofit once when creating a single instance
    private Api() {
        // Implement a method to build your retrofit
        buildRetrofit();
    }

    private void buildRetrofit() {

        Retrofit.Builder builder = new Retrofit.Builder().
                baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        this.userClient = retrofit.create(UserClient.class);

    }

    public UserClient getUserClient() {
        return this.userClient;
    }

    public Token RefreshToken(final Token token, final Context context){
        final Token newToken = new Token();
        if(token != null) {
            UserClient userClient = Api.getInstance().getUserClient();
            Call<Token> call = userClient.refreshToken(token.getRefresh());
            call.enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    Log.d(LOG_TAG, response.toString());
                    if (response.isSuccessful()) {
                        newToken.setRefresh(response.body().getRefresh());
                        newToken.setToken(response.body().getToken());
                    }else{
                        SharedPreferences preferences = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        preferences.edit().remove(Constants.SHARED_PREF_TOKEN).apply();
                        preferences.edit().remove(Constants.SHARED_PREF_REF).apply();
                    }
                }
                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    Log.d(LOG_TAG, "Error con el token", t);
                }
            });
        }
        Log.d(LOG_TAG, "TOKEN: " + newToken.getToken() + "    " + newToken.getRefresh());
        return (newToken.getToken() == null || newToken.getRefresh() == null) ? null : newToken;
    }



}