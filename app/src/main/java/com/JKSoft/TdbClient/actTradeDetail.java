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
    TextView tvTarget;
    TextView tvStopLoss;
    TextView tvOrderStatus;
    TextView tvOrderNum;
    TextView tvMethod;
    TextView tvEstimatedTradeStatus;



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
        tvTarget  = (TextView) findViewById(R.id.tvTarget);
        tvStopLoss = (TextView) findViewById(R.id.tvStopLoss);
        tvOrderStatus = (TextView) findViewById(R.id.tvOrderStatus);
        tvOrderNum = (TextView) findViewById(R.id.tvOrderNum);
        tvMethod = (TextView) findViewById(R.id.tvMethod);
        tvEstimatedTradeStatus = (TextView) findViewById(R.id.tvEstimatedTradeStatus);

        String strNumberFormat = "%1$.4f";

        tvSymbol.setText(tradeRecord.getSymbol());
        tvLevelPrice.setText(String.format(strNumberFormat,tradeRecord.getLevelPrice()));
        tvDirection.setText(tradeRecord.getDirection());
        //tvTarget.setText(String.format(strNumberFormat,tradeRecord.getTarget));
        if (tradeRecord.getOrderStatus() != null) tvTarget.setText(tradeRecord.getOrderStatus());
        if (tradeRecord.getEstimatedTradeStatus() != null) tvEstimatedTradeStatus.setText(tradeRecord.getEstimatedTradeStatus());
        if (tradeRecord.getOrderNumber() != null) tvOrderNum.setText(tradeRecord.getOrderNumber().toString());



    }
}
