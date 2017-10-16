package com.sam.teamd.samandroidclient.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.sam.teamd.samandroidclient.model.Token;
import com.sam.teamd.samandroidclient.ui.MainActivity;
import com.sam.teamd.samandroidclient.util.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
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
    private Context context;
    private boolean isRefresh = false;

    public static final String BASE_URL = "http://192.168.0.11:4000/";

    // Keep yovur services here, build them in buildRetrofit method later
    private UserClient userClient;


    public static Api getInstance(Context context) {
        if (instance == null) {
            instance = new Api(context);
        }

        return instance;
    }



    // Build retrofit once when creating a single instance
    private Api(Context context) {
        // Implement a method to build your retrofit
        buildRetrofit();
        this.context = context;
    }


    private void buildRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        okhttp3.Response response = chain.proceed(request);
                        // todo deal with the issues the way you need to
                        if (response.code() == 401 && !isRefresh) {
                            isRefresh = true;
                            RefreshToken();
                            return response;
                        }
                        return response;
                    }
                })
                .build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        this.userClient = retrofit.create(UserClient.class);

    }

    public UserClient getUserClient() {
        return this.userClient;
    }

    public void RefreshToken(){
        final SharedPreferences sharedPref = context.getSharedPreferences(Constants.SHARED_PREF_NAME,Context.MODE_PRIVATE);
        String token = sharedPref.getString(Constants.SHARED_PREF_TOKEN, null);
        final String refresh = sharedPref.getString(Constants.SHARED_PREF_REF, null);
        if(token != null && refresh != null) {
            final Token newToken = new Token(token, refresh);
            Call<Token> call = userClient.refreshToken(newToken.getRefresh());
            call.enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    Log.d(LOG_TAG, response.toString());
                    if (response.isSuccessful()) {
                        sharedPref.edit().putString(Constants.SHARED_PREF_TOKEN, response.body().getToken()).apply();
                        sharedPref.edit().putString(Constants.SHARED_PREF_REF, response.body().getRefresh()).apply();
                        isRefresh = false;
                    }else{
                        sharedPref.edit().remove(Constants.SHARED_PREF_TOKEN).apply();
                        sharedPref.edit().remove(Constants.SHARED_PREF_REF).apply();
                        isRefresh = false;
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    Log.d(LOG_TAG, "Error con el token", t);
                }
            });
        }
    }



}