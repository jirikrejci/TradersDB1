package com.JKSoft.TdbClient.restAdapters;

import com.JKSoft.TdbClient.apiInterfaces.TradersDbApi;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.Response;

/**
 * Created by Jirka on 19.8.2016.
 */
public class TradersDbRestAdapter implements TradersDbApi {
    protected final String TAG = "JK: " + getClass().getSimpleName();
    protected RestAdapter mRestAdapter;
    protected TradersDbApi mTdbApi;
    static final String TDB_API_URL = "http://tradesdb-android-gcm.appspot.com";

    public TradersDbRestAdapter() {

        mRestAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(TDB_API_URL)
                .build();

        mTdbApi = mRestAdapter.create(TradersDbApi.class);

    }

    @Override

    public void getHello(Callback<Response> callBack) {
        mTdbApi.getHello(callBack);
    }

    @Override
    public void postHello(String name, Callback<Response> callback) {
        mTdbApi.postHello(name, callback);
    }
}


