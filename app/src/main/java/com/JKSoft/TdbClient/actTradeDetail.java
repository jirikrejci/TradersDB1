package com.JKSoft.TdbClient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.JKSoft.DataStructures.TradeRecord;
import com.example.jirka.TdbClient.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class actTradeDetail extends AppCompatActivity {

    TextView tvSymbol;
    TextView tvLevelPrice;
    TextView tvDirection;
    TextView tvTP;
    TextView tvSl;
    TextView tvOrderStatus;
    TextView tvOrderNum;
    TextView tvMethod;
    TextView tvEstimatedTradeStatus;
    TextView tvTpProposed;
    TextView tvTpManual;
    TextView tvSlPips;
    TextView tvSlMoney;
    TextView tvRrrPlanned;
    TextView tvLevelDistance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_trade_detail);

        Intent intent = getIntent();
        String jsonRecordStr = intent.getStringExtra("TRADE_RECORD");
        Gson gson = new GsonBuilder().create();
        TradeRecord tradeRecord = gson.fromJson(jsonRecordStr, TradeRecord.class);

        tvSymbol = (TextView) findViewById(R.id.tvSymbol);
        tvLevelPrice  = (TextView) findViewById(R.id.tvLevelPrice);
        tvDirection  = (TextView) findViewById(R.id. tvDirection);
        tvTP = (TextView) findViewById(R.id.tvTP);
        tvSl = (TextView) findViewById(R.id.tvSL);
        tvOrderStatus = (TextView) findViewById(R.id.tvOrderStatus);
        tvOrderNum = (TextView) findViewById(R.id.tvOrderNum);
        tvMethod = (TextView) findViewById(R.id.tvMethod);
        tvEstimatedTradeStatus = (TextView) findViewById(R.id.tvEstimatedTradeStatus);
        tvTpProposed =  (TextView) findViewById(R.id.tvTpProposed);
        tvTpManual =  (TextView) findViewById(R.id.tvTpManual);
        tvSlPips = (TextView) findViewById(R.id.tvSlPips);
        tvSlMoney = (TextView) findViewById(R.id.tvSlMoney);
        tvRrrPlanned = (TextView) findViewById(R.id.tvRrrPlanned);
        tvLevelDistance = (TextView) findViewById(R.id.tvLevelDistance);








        String strNumberFormat = "%1$.4f";

        tvSymbol.setText(tradeRecord.getSymbol());
        tvLevelPrice.setText(String.format(strNumberFormat,tradeRecord.getLevelPrice()));
        tvDirection.setText(tradeRecord.getDirection());
        if (tradeRecord.getOrderStatus() != null) tvOrderStatus.setText(tradeRecord.getOrderStatus());
        if (tradeRecord.getEstimatedTradeStatus() != null) tvEstimatedTradeStatus.setText(tradeRecord.getEstimatedTradeStatus());
        if (tradeRecord.getOrderNumber() != null) tvOrderNum.setText(tradeRecord.getOrderNumber().toString());
        if (tradeRecord.getTpProposed() != null) tvTpProposed.setText(String.format(strNumberFormat,tradeRecord.getTpProposed()));
        if (tradeRecord.getTpManual() != null) tvTpManual.setText(String.format(strNumberFormat,tradeRecord.getTpManual()));

        if (tradeRecord.getSL() != null) tvSl.setText(String.format(strNumberFormat, tradeRecord.getSL()));
        if (tradeRecord.getSlPips() != null) tvSlPips.setText(String.format("%.1f",tradeRecord.getSlPips()));
        if (tradeRecord.getSlInMoney() != null) tvSlMoney.setText(String.format("%.2f EUR",tradeRecord.getSlInMoney()));
        if (tradeRecord.getRrrPlanned() != null) tvRrrPlanned.setText(String.format("%.2f%%",tradeRecord.getRrrPlanned()));
        if (tradeRecord.getLevelDistance() != null) tvLevelDistance.setText(String.format("%.0f", tradeRecord.getLevelDistance()));


    }
}
