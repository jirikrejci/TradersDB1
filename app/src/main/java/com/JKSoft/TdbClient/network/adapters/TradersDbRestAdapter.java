package com.JKSoft.TdbClient.network.adapters;

import com.JKSoft.TdbClient.model.data.RelevantTradesExch;
import com.JKSoft.TdbClient.network.api.TradersDbApi;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.Response;

/**
 * TDB (Trader Database) web API implementation
 * Created by Jirka on 19.8.2016.
 */
public class TradersDbRestAdapter implements TradersDbApi {
    protected final String TAG = "JK: " + getClass().getSimpleName();
    private RestAdapter mRestAdapter;
    private TradersDbApi mTdbApi;
    private static final String TDB_API_URL = "http://tradesdb-android-gcm.appspot.com";

    /**
     * API adapter initialisation
     */
    public TradersDbRestAdapter() {

        mRestAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(TDB_API_URL)
                .build();

        mTdbApi = mRestAdapter.create(TradersDbApi.class);
    }

    /**
     * Testing purposes only - contacts server and gets "Hello"
     * @param callBack - callback called when done with parameter Response
     */

    @Override
    public void getHello(Callback<Response> callBack) {
        mTdbApi.getHello(callBack);
    }

    /**
     * Testing purposes only - contacts server, post name and returns "Hello name"
     * @param callback - callback called when done with parameter Response
     */

    @Override
    public void postHello(String name, Callback<Response> callback) {
        mTdbApi.postHello(name, callback);
    }

    /**
     * Gets RelevantTradesExch object from TDB server
     * @return
     */
    @Override
    public RelevantTradesExch getActualTrades() {
        return mTdbApi.getActualTrades();
    }
}


