package com.ace.legend.test;

import com.ace.legend.test.model.Flower;
import com.ace.legend.test.model.ResponseData;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by rohan on 6/24/15.
 */
public interface ServerCalls {

    @GET("/feeds/flowers.json")
    public void getFeed(Callback<List<Flower>> response);

    @FormUrlEncoded
    @POST("/register.php")
    public void signUp(@Field("Username") String username, @Field("Password") String password, Callback<ResponseData> response);

    @FormUrlEncoded
    @POST("/login.php")
    public void login(@Field("Username") String username, @Field("Password") String password, Callback<ResponseData> response);
}
