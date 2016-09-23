package com.JKSoft.TdbClient;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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


    public static FrgTradeDetail newInstance (int p, String jsonStr) {
        FrgTradeDetail frgTradeDetail = new FrgTradeDetail();
        Bundle arguments = new Bundle();
        arguments.putInt("SELECTED_ITEM", p);
        arguments.putString("JSON", jsonStr);
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
        View view = inflater.inflate(R.layout.act_trade_detail, container, false);
        ButterKnife.bind(this, view);

        Bundle arguments = getArguments();

        if (arguments != null) {

            String jsonRecordStr = arguments.getString("JSON");
            int p = arguments.getInt("SELECTED_ITEM");
            Gson gson = new GsonBuilder().create();
            TradeRecord tradeRecord = gson.fromJson(jsonRecordStr, TradeRecord.class);

            displayTradeRecord(tradeRecord);

            /*
            String strNumberFormat = "%1$.4f";

            tvSymbol.setText(tradeRecord.getSymbol());
            tvLevelPrice.setText(String.format(strNumberFormat, tradeRecord.getLevelPrice()));
            tvDirection.setText(tradeRecord.getDirection());
            if (tradeRecord.getOrderStatus() != null)
                tvOrderStatus.setText(tradeRecord.getOrderStatus());
            if (tradeRecord.getEstimatedTradeStatus() != null)
                tvEstimatedTradeStatus.setText(tradeRecord.getEstimatedTradeStatus());
            if (tradeRecord.getOrderNumber() != null)
                tvOrderNum.setText(tradeRecord.getOrderNumber().toString());
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
*/
        }
        return view;
    }

    public void displayTradeRecordJson(String jsonRecordStr) {
        Gson gson = new GsonBuilder().create();
        TradeRecord tradeRecord = gson.fromJson(jsonRecordStr, TradeRecord.class);
        displayTradeRecord(tradeRecord);

    }


    public void displayTradeRecord (TradeRecord tradeRecord) {    // TODO ?? zeptat se jestli statická třída nemá přístup k dynamickým proměnným třídy - vypadá to tak

        String strNumberFormat = "%1$.4f";

        tvSymbol.setText(tradeRecord.getSymbol());
        tvLevelPrice.setText(String.format(strNumberFormat, tradeRecord.getLevelPrice())); // TODO použít String format
        tvDirection.setText(tradeRecord.getDirection());
        if (tradeRecord.getOrderStatus() != null)
            tvOrderStatus.setText(tradeRecord.getOrderStatus());
        if (tradeRecord.getEstimatedTradeStatus() != null)
            tvEstimatedTradeStatus.setText(tradeRecord.getEstimatedTradeStatus());
        if (tradeRecord.getOrderNumber() != null)
            tvOrderNum.setText(tradeRecord.getOrderNumber().toString());
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

    }


}
