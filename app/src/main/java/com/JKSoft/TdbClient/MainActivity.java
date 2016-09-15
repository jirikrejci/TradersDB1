package com.JKSoft.TdbClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.JKSoft.Networking.NetworkFms.Ftp;
import com.JKSoft.TdbClient.Convertors.Convertors;
import com.JKSoft.TdbClient.RestAdapters.TradersDbRestAdapter;
import com.example.MyFirstAsLibraryClass;
import com.example.jirka.TdbClient.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTdbRestAdapter = new TradersDbRestAdapter();

        tvDisplay = (TextView) findViewById(R.id.tvDisplay);

        //TODO smazat nsáledující testy
        MyFirstAsLibraryClass.show();
        MyFirstAsLibraryClass.sayHello("Jirko");

     //   NetWorkLibrarySharedClass.show();


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