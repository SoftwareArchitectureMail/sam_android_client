package com.sam.teamd.samandroidclient.service;

import com.sam.teamd.samandroidclient.model.Login;
import com.sam.teamd.samandroidclient.model.Mail;
import com.sam.teamd.samandroidclient.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by david on 16/10/17.
 */

public interface MailClient {

    @POST("send")
    Call<ResponseBody> sendMail (@Header("Authorization") String authToken, @Body Mail mail);
}
