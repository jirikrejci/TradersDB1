package com.JKSoft.TdbClient.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.JKSoft.TdbClient.fragments.FrgTradeDetail;
import com.example.jirka.TdbClient.R;

import butterknife.BindView;

/**
 * Created by Jirka on 3.10.2016.
 */
public class actTradeDetail extends AppCompatActivity {

    @BindView(R.id.tvSymbol)  TextView tvSymbol;
    /**
     * Perform initialization of all fragments and loaders.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_trade_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        FrgTradeDetail frgTradeDetail = new FrgTradeDetail();

        FragmentManager fragmentManager = getSupportFragmentManager();
        frgTradeDetail.setArguments(getIntent().getExtras());  // TODO - pořešit přes newInstance


        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frgContainerTradesDetail, frgTradeDetail);
        fragmentTransaction.commit();

    }
}
