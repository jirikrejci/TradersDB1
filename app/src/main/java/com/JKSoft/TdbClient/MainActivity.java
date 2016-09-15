package com.JKSoft.TdbClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.JKSoft.DataStructures.RelevantTradesExch;
import com.JKSoft.Networking.Gson.JsonConversions;
import com.JKSoft.Networking.NetworkFms.Ftp;
import com.JKSoft.TdbClient.Convertors.Convertors;
import com.JKSoft.TdbClient.RestAdapters.TradersDbRestAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTdbRestAdapter = new TradersDbRestAdapter();

        tvDisplay = (TextView) findViewById(R.id.tvDisplay);

    }


    private Callback<Response> mHelloGetCallback = new Callback<Response>() {
        @Override
        public void success(Response result, Response response) {
            tvDisplay.setText(Convertors.responseBodyToString(response));
            Log.d(TAG, "Response body:" + Convertors.responseBodyToString(response));
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
            tvDisplay.setText(Convertors.responseBodyToString(response));
            Log.d(TAG, "Response body:" + Convertors.responseBodyToString(response));
        }

        @Override
        public void failure(RetrofitError error) {
            tvDisplay.setText("failure: " + error.toString());
            Log.d (TAG, "failure: " + error);
        }
    };









    public void btnHelloGet_onClick(View view) {
        tvDisplay.setText("");
        Toast.makeText(this, "btnHello GET OnClick",Toast.LENGTH_SHORT).show();
        mTdbRestAdapter.getHello(mHelloGetCallback);

    }

    public void btnHelloPost_onClick(View view) {
        tvDisplay.setText("");
        Toast.makeText(this, "btnHello PUT OnClick",Toast.LENGTH_SHORT).show();
        mTdbRestAdapter.postHello("Jirko", mHelloPostCallback);

    }

    public void btnReadFtpFromNas_onClick(View view) {
        tvDisplay.setText("");
        AsyncTask <String, Void, String> task  =  new AsyncTask<String, Void , String>() {
            @Override
            protected String doInBackground(String... strings) {
                return Ftp.readStringFromFtp("/FilesDB/RelevantTrades.json");  // OK
            }

            @Override
            protected void onPostExecute(String jsonStr) {
                super.onPostExecute(jsonStr);
                tvDisplay.setText(jsonStr);
                Gson gson = new GsonBuilder().create();
                relevantTradesExch = gson.fromJson(jsonStr, RelevantTradesExch.class);

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
            tvDisplay.setText(JsonConversions.getJSonPretty(relevantTradesExch));
        } else {
            Toast.makeText(this, "no TradeDB data",Toast.LENGTH_SHORT).show();
        }
    }
}
;