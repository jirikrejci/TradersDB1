package com.example.jirka.tradersdb1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jirka.tradersdb1.Convertors.Convertors;
import com.example.jirka.tradersdb1.NasServer.NasFtp2;
import com.example.jirka.tradersdb1.RestAdapters.TradersDbRestAdapter;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    protected final String TAG = "JK:" + getClass().getSimpleName();
    private TradersDbRestAdapter mTdbRestAdapter;
    private TextView tvDisplay;

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
                return NasFtp2.readStringFromFtpFile("/FilesDB/RelevantTrades.json");  // OK
            }

            @Override
            protected void onPostExecute(String text) {
                super.onPostExecute(text);
                tvDisplay.setText(text);

         /*       RelevantTradesExch tradesExch;
                Gson gson = new GsonBuilder().create();
                tradesExch = gson.fromJson(text,RelevantTradesExch.class);
*/



            }
        };
        task.execute("Ahoj");

    }
}
;