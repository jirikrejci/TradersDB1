package com.JKSoft.TdbClient.app_old;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.JKSoft.DataStructures.RelevantTradesExch;
import com.JKSoft.DataStructures.TradeRecord;
import com.JKSoft.Networking.fms.Ftp;
import com.JKSoft.TdbClient.TradesRecyclerView.adapter.TradesListAdapter;
import com.example.jirka.TdbClient.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class actTradesOverview extends AppCompatActivity implements TradesListAdapter.ItemClickCallback{

    RecyclerView recView;
    TradesListAdapter tradesListAdapter;

    List<TradeRecord> tradeRecordList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_trades_overview);

        tradeRecordList = new ArrayList<>();    // TODO předělat podle příkladu
        Intent intent = getIntent();
        //Bundle bundle = intent.getExtras("EXTRAS");
        String jsonStr = intent.getStringExtra("DATA");


        if (jsonStr != null || jsonStr == "") {
            Gson gson = new GsonBuilder().create();
            RelevantTradesExch tradesExch = gson.fromJson(jsonStr, RelevantTradesExch.class);
            ArrayList<TradeRecord> tradeRecordList =  tradesExch.getTrades();
        }


        recView = (RecyclerView) findViewById(R.id.rvTradesList);
        tradesListAdapter = new TradesListAdapter(tradeRecordList, this);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(tradesListAdapter);
        tradesListAdapter.setItemClickCallback(this);  // aby šlo zadat this, musí se implementovat interface

    }



    public void btnReloadData_OnClick(View view) {

        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return Ftp.readStringFromFtp("/FilesDB/RelevantTrades.json");  // OK
            }

            @Override
            protected void onPostExecute(String jsonString) {
                super.onPostExecute(jsonString);
                Gson gson = new GsonBuilder().create();
                RelevantTradesExch relevantTradesExch = gson.fromJson(jsonString, RelevantTradesExch.class);

                ArrayList<TradeRecord> newTradeRecordList = relevantTradesExch.getTrades();

                tradeRecordList.clear();


                for (TradeRecord tradeRecord: newTradeRecordList) {              // TODO pokud nepůjde, alespoˇn předělat na for each - nebo rovnou předělat, ať se něco nauíčm
                    tradeRecordList.add(tradeRecord);
                }
                tradesListAdapter.notifyDataSetChanged();
         /*       RelevantTradesExch tradesExch;
                Gson gson = new GsonBuilder().create();
                tradesExch = gson.fromJson(text,RelevantTradesExch.class);
*/
            }
        };
        task.execute("Ahoj");

    }

    @Override
    public void onItemClick(int p) {
        Intent intent = new Intent(this, actTradeDetail_old.class);
        TradeRecord tradeRecord = tradeRecordList.get(p);        //TODO očetřit aby se správně pracovalo jen s jedním zdrojem dat !!! Array nebo List tradeRecords je zde stejně null
        Gson gson = new GsonBuilder().create();                     // TODO zeptat se kluku, jak co nejefektivněji předávat record do nové aktivity. Možná to ale také vtřeší framy
        String jsonRecordStr = gson.toJson(tradeRecord);
        intent.putExtra("TRADE_RECORD", jsonRecordStr);
        startActivity(intent);
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p/>
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.</p>
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.mReloadDataFromServer:
            case R.id.mReadDataFromFtp:
                Toast.makeText(this, "JK: Reload data requested", Toast.LENGTH_LONG).show();
                break;
            case R.id.mWipeDataFromMemory:
                Toast.makeText(this, "JK: Wipe data from memory", Toast.LENGTH_LONG).show();
                break;
            case R.id.mAbout:
                aboutMenuItem();
                break;
            default:
                Toast.makeText(this, "\"" + item.getTitle() + "\" menu item selected", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    private void aboutMenuItem() {
        new AlertDialog.Builder(this)
                .setTitle("About")
                .setMessage("Trader DB by Jiri Krejci C")
                .setIcon(R.mipmap.ic_launcher)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }
                ).show();
    }
}


