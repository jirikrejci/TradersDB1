package com.JKSoft.TdbClient.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.JKSoft.DataStructures.TradeRecord;
import com.example.jirka.TdbClient.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FrgTradeDetail extends Fragment {

    public static final String SELECTED_RECORD_JSON = "SELECTED_RECORD_JSON";
    public static final String SELECTED_ITEM_POS = "SELECTED_ITEM_POS";


    @BindView (R.id.tvSymbol) TextView tvSymbol;

    @BindView (R.id.tvLevelPrice) TextView tvLevelPrice;
    @BindView (R.id.tvDirection) TextView tvDirection;
    @BindView (R.id.tvTP) TextView tvTP;
    @BindView (R.id.tvSL) TextView tvSL;
    @BindView (R.id.tvOrderStatus) TextView tvOrderStatus;
    @BindView (R.id.tvOrderNum) TextView tvOrderNum;
    @BindView (R.id.tvMethod) TextView tvMethod;
    @BindView (R.id.tvEstimatedTradeStatus) TextView tvEstimatedTradeStatus;
    @BindView (R.id.tvTpProposed) TextView tvTpProposed;
    @BindView (R.id.tvTpManual) TextView tvTpManual;
    @BindView (R.id.tvSlPips) TextView tvSlPips;
    @BindView (R.id.tvSlMoney) TextView tvSlMoney;
    @BindView (R.id.tvRrrPlanned) TextView tvRrrPlanned;
    @BindView (R.id.tvLevelDistance) TextView tvLevelDistance;
    @BindView (R.id.tvTradeId) TextView tvTradeId;
    @BindView (R.id.tvTradeRecordStatus) TextView tvTradeRecordStatus;


    public static FrgTradeDetail newInstance (int p, String jsonStr) {
        FrgTradeDetail frgTradeDetail = new FrgTradeDetail();
        Bundle arguments = new Bundle();
        arguments.putInt(SELECTED_ITEM_POS, p);
        arguments.putString(SELECTED_RECORD_JSON, jsonStr);
        frgTradeDetail.setArguments(arguments);
        return frgTradeDetail;
    }


     /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p/>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lay_trade_detail, container, false);
        ButterKnife.bind(this, view);

        Bundle arguments = getArguments();

        if (arguments != null) {

            String jsonRecordStr = arguments.getString(SELECTED_RECORD_JSON);
            int p = arguments.getInt(SELECTED_ITEM_POS);
            Gson gson = new GsonBuilder().create();
            TradeRecord tradeRecord = gson.fromJson(jsonRecordStr, TradeRecord.class);

            displayTradeRecord(tradeRecord);

        }
        return view;
    }


    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("JK", "in onViewCreated");
    }

    public void displayTradeRecordJson(String jsonRecordStr) {
        Gson gson = new GsonBuilder().create();
        TradeRecord tradeRecord = gson.fromJson(jsonRecordStr, TradeRecord.class);
        displayTradeRecord(tradeRecord);

    }


    public void displayTradeRecord (TradeRecord tradeRecord) {    // TODO ?? zeptat se jestli statická třída nemá přístup k dynamickým proměnným třídy - vypadá to tak

        String strNumberFormat = "%1$.4f";

        tvSymbol.setText(tradeRecord.getSymbol());
        tvLevelPrice.setText(String.format(strNumberFormat, tradeRecord.getLevelPrice())); // TODO prio 2 - dořešit LOCALE ve String.format
        tvDirection.setText(tradeRecord.getDirection());
        if (tradeRecord.getOrderStatus() != null)
            tvOrderStatus.setText(tradeRecord.getOrderStatus());
        if (tradeRecord.getEstimatedTradeStatus() != null)
            tvEstimatedTradeStatus.setText(tradeRecord.getEstimatedTradeStatus());
        if (tradeRecord.getOrderNumber() != null)
            tvOrderNum.setText(tradeRecord.getOrderNumber().toString());
            else
            tvOrderNum.setText("");             // TODO pořešit null i u ostatních, aby nezůstávaly viset hodnoty z jiných

        if (tradeRecord.getTpProposed() != null)
            tvTpProposed.setText(String.format(strNumberFormat, tradeRecord.getTpProposed()));
        if (tradeRecord.getTpManual() != null)
            tvTpManual.setText(String.format(strNumberFormat, tradeRecord.getTpManual()));

        if (tradeRecord.getSL() != null)
            tvSL.setText(String.format(strNumberFormat, tradeRecord.getSL()));
        if (tradeRecord.getSlPips() != null)
            tvSlPips.setText(String.format("%.1f", tradeRecord.getSlPips()));
        if (tradeRecord.getSlInMoney() != null)
            tvSlMoney.setText(String.format("%.2f EUR", tradeRecord.getSlInMoney()));
        if (tradeRecord.getRrrPlanned() != null)
            tvRrrPlanned.setText(String.format("%.2f%%", tradeRecord.getRrrPlanned()));
        if (tradeRecord.getLevelDistance() != null)
            tvLevelDistance.setText(String.format("%.0f", tradeRecord.getLevelDistance()));
        if (tradeRecord.getMethod() != null) tvMethod.setText(tradeRecord.getMethod()); else tvMethod.setText("");
        if (tradeRecord.getTradeId() != null) tvTradeId.setText(String.format("%d",tradeRecord.getTradeId())); else tvTradeId.setText("");
        if (tradeRecord.getTradeRecordStatus() != null) tvTradeRecordStatus.setText(tradeRecord.getTradeRecordStatus()); else tvTradeRecordStatus.setText("");



    }


}
