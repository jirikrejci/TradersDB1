package com.JKSoft.TdbClient.app_old;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.JKSoft.TdbClient.dataStructures.RelevantTradesExch;
import com.JKSoft.Networking.fms.Ftp;
import com.JKSoft.Networking.Gson.JsonConversions;
import com.JKSoft.TdbClient.Convertors.HttpConvertor;
import com.JKSoft.TdbClient.RestAdapters.TradersDbRestAdapter;
import com.JKSoft.TdbClient.app.actFragmentedMain;
import com.example.jirka.TdbClient.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

//Pokus added in nefunkcni branch
// pokus added in nefunkcni TestBranch 2
// funkcni knihovny pred novou branchi

public class MainActivity extends Activity {

    protected final String TAG = "JK:" + getClass().getSimpleName();
    private TradersDbRestAdapter mTdbRestAdapter;
    private TextView tvDisplay;
    private RelevantTradesExch relevantTradesExch;
    private String jsonStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main_old_layout);
        mTdbRestAdapter = new TradersDbRestAdapter();

        tvDisplay = (TextView) findViewById(R.id.tvDisplay);

    }


    private Callback<Response> mHelloGetCallback = new Callback<Response>() {
        @Override
        public void success(Response result, Response response) {
            tvDisplay.setText(HttpConvertor.responseBodyToString(response));
            Log.d(TAG, "Response body:" + HttpConvertor.responseBodyToString(response));
        }

        @Override
        public void failure(RetrofitError error) {
            tvDisplay.setText("failure: " + error.toString());
            Log.d(TAG, "failure: " + error);
        }
    };

    private Callback<Response> mHelloPostCallback = new Callback<Response>() {
        @Override
        public void success(Response result, Response response) {
            tvDisplay.setText(HttpConvertor.responseBodyToString(response));
            Log.d(TAG, "Response body:" + HttpConvertor.responseBodyToString(response));
        }

        @Override
        public void failure(RetrofitError error) {
            tvDisplay.setText("failure: " + error.toString());
            Log.d(TAG, "failure: " + error);
        }
    };


    public void btnHelloGet_onClick(View view) {
        tvDisplay.setText("");
        Toast.makeText(this, "btnHello GET OnClick", Toast.LENGTH_SHORT).show();
        mTdbRestAdapter.getHello(mHelloGetCallback);

    }

    public void btnHelloPost_onClick(View view) {
        tvDisplay.setText("");
        Toast.makeText(this, "btnHello PUT OnClick", Toast.LENGTH_SHORT).show();
        mTdbRestAdapter.postHello("Jirko", mHelloPostCallback);

    }

    public void btnReadFtpFromNas_onClick(View view) {
        tvDisplay.setText("");
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return Ftp.readStringFromFtp("/FilesDB/RelevantTrades.json");  // OK
            }

            @Override
            protected void onPostExecute(String jsonString) {
                super.onPostExecute(jsonString);
                tvDisplay.setText(jsonString);
                Gson gson = new GsonBuilder().create();
                relevantTradesExch = gson.fromJson(jsonString, RelevantTradesExch.class);

                jsonStr = jsonString;


         /*       RelevantTradesExch tradesExch;
                Gson gson = new GsonBuilder().create();
                tradesExch = gson.fromJson(text,RelevantTradesExch.class);
*/


            }
        };
        task.execute("Ahoj");

    }

    public void btnShowJsonPretty(View view) {
        if (relevantTradesExch != null) {
            tvDisplay.setText(JsonConversions.getJsonPretty(relevantTradesExch));
        } else {
            Toast.makeText(this, "no TradeDB data", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnShowTradesView(View view) {

        Intent intent = new Intent(this, actFragmentedMain.class);

        Bundle bundle = new Bundle();
        // TODO - vymyslet, jak předat data jako parcelable
        //bundle.putString( "DATA", jsonStr);  // TODO vymysklet, jakp ředat KEY, aby se editoval jen na jednom místě
        intent.putExtra("DATA", jsonStr);    // TODO lze i putextra přímo
        startActivity(intent);

    }
}