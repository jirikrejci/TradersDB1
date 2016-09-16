package com.JKSoft.TdbClient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.JKSoft.DataStructures.RelevantTradesExch;
import com.JKSoft.DataStructures.TradeRecord;
import com.JKSoft.TdbClient.TradesRecyclerView.adapter.TradesListAdapter;
import com.example.jirka.TdbClient.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class actTradesOverview extends AppCompatActivity {

    RecyclerView recView;
    TradesListAdapter tradesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_trades_overview);

        Intent intent = getIntent();
        //Bundle bundle = intent.getExtras("EXTRAS");

        String jsonStr = intent.getStringExtra("DATA");

        Gson gson = new GsonBuilder().create();
        RelevantTradesExch tradesExch = gson.fromJson(jsonStr, RelevantTradesExch.class);
        TradeRecord [] tradeRecords;   //TODO překkopat, jestli rovnou nejde do listu
        tradeRecords = tradesExch.getTrades();



        recView = (RecyclerView) findViewById(R.id.rvTradesList);

        List<TradeRecord> tradeRecordList = new ArrayList<>();    // TODO předělat podle příkladu
        for (int i = 0; i< tradeRecords.length; i++) {              // TODO pokud nepůjde, alespoˇn předělat na for each - nebo rovnou předělat, ať se něco nauíčm
            tradeRecordList.add(tradeRecords[i]);
        }



        tradesListAdapter = new TradesListAdapter(tradeRecordList, this);


        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(tradesListAdapter);



    }

}
