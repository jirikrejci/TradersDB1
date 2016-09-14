package com.JKSoft.TdbClient.ApiInterfaces;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by Jirka on 19.8.2016.
 */
public interface  TradersDbApi {

    //  /hello GET
    @GET ("/hello")
    void getHello (Callback<Response> callBack);

    // /hello POST
    @FormUrlEncoded
    @POST("/hello")
    void postHello (
            @Field("name") String name,
            Callback<Response> callback
    );

}
