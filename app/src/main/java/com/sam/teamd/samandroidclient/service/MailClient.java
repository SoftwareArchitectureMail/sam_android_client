package com.sam.teamd.samandroidclient.service;

import com.sam.teamd.samandroidclient.model.Login;
import com.sam.teamd.samandroidclient.model.Mail;
import com.sam.teamd.samandroidclient.model.User;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by david on 16/10/17.
 */

public interface MailClient {

    @POST("send")
    Call<ResponseBody> sendMail (@Header("Authorization") String authToken, @Body Mail mail);

    @GET("inbox")
    Call<List<Mail>> getInbox (@Header("Authorization") String authToken, @QueryMap Map<String, String> options);

    @GET("sent")
    Call<List<Mail>> getSent (@Header("Authorization") String authToken, @QueryMap Map<String, String> options);

    @GET("draft")
    Call<List<Mail>> getDraft (@Header("Authorization") String authToken, @QueryMap Map<String, String> options);
}
