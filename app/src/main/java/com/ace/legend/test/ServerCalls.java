package com.ace.legend.test;

import com.ace.legend.test.model.Flower;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by rohan on 6/24/15.
 */
public interface ServerCalls {

    @GET("/feeds/flowers.json")
    public void getFeed(Callback<List<Flower>> response);

}
