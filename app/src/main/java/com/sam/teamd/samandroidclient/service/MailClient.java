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
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by david on 16/10/17.
 */

public interface MailClient {

    @POST("send")
    Call<ResponseBody> sendMail (@Header("Authorization") String authToken, @Body Mail mail);

    @GET("inbox/{id}")
    Call<Mail> getSingleMail (@Header("Authorization") String authToken, @Path("id") String id);

    @GET("sent/{id}")
    Call<Mail> getSingleSent (@Header("Authorization") String authToken, @Path("id") String id);

    @GET("draft/{id}")
    Call<Mail> getSingleDraft (@Header("Authorization") String authToken, @Path("id") String id);

    @GET("inbox")
    Call<List<Mail>> getInbox (@Header("Authorization") String authToken, @QueryMap Map<String, String> options);

    @GET("sent")
    Call<List<Mail>> getSent (@Header("Authorization") String authToken, @QueryMap Map<String, String> options);

    @GET("draft")
    Call<List<Mail>> getDraft (@Header("Authorization") String authToken, @QueryMap Map<String, String> options);

    @PUT("inbox/{id}")
    Call<ResponseBody> putRead (@Header("Authorization") String authToken, @Path("id") String id, @Body Map<String, String> response);

    @DELETE("inbox/{id}")
    Call<Mail> delSingleMail (@Header("Authorization") String authToken, @Path("id") String id);
}
