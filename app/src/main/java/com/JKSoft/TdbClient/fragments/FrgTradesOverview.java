package com.JKSoft.TdbClient.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.JKSoft.DataStructures.TradeRecord;
import com.JKSoft.TdbClient.Model.TdbDataSource;
import com.JKSoft.TdbClient.TradesRecyclerView.adapter.TradesListAdapter;
import com.example.jirka.TdbClient.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FrgTradesOverview extends Fragment implements TradesListAdapter.ItemClickCallback{

    RecyclerView recView;
    TradesListAdapter tradesListAdapter;
    List<TradeRecord> tradeRecordList;

    @BindView(R.id.pbDataLoadProgressBar)  ProgressBar progressBar;

    Context context;

    SelectedItemListener selectedItemListener;  // proměnná pro uložení implementovaného listeneru

    // SelectedItemListener interface
    public interface SelectedItemListener
    {
        public void onSelectedItem (int p, String itemJson);
    }

    // setter pro SelectedItemListener
    public void setSelectedItemListener (SelectedItemListener selectedItemListener) {
        this.selectedItemListener = selectedItemListener;
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lay_trades_overview, container, false);  // false je zde strašně, důležité, jinak to padá
        ButterKnife.bind(this, view);


        tradeRecordList = new ArrayList<>();    // TODO předělat podle příkladu

        context = getContext();

        // RecycleView
        recView = (RecyclerView) view.findViewById(R.id.rvTradesList);
        tradesListAdapter = new TradesListAdapter(tradeRecordList, context);
        recView.setLayoutManager(new LinearLayoutManager(context));
        recView.setItemAnimator(null);
        recView.setAdapter(tradesListAdapter);

        tradesListAdapter.setItemClickCallback(this);  // aby šlo zadat this, musí se implementovat interface


        // natavení listeneru pro click v seznamu

        reloadData();

        return view;
    }

    private void clearTradesList() {
        tradeRecordList.clear();
        tradesListAdapter.notifyDataSetChanged();

    }


    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context
     */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        selectedItemListener = (SelectedItemListener) context;
    }


   

    public void reloadData () {

        clearTradesList();
        progressBar.setVisibility(View.VISIBLE  );


        AsyncTask<String, Void, List<TradeRecord>> task = new AsyncTask<String, Void, List<TradeRecord>>() {
            @Override
            protected ArrayList<TradeRecord> doInBackground(String... strings) {
                return TdbDataSource.getActualTradeRecords(context);
            }

            @Override
            protected void onPostExecute(List<TradeRecord> newTradeRecordList) {
                super.onPostExecute(tradeRecordList);
                progressBar.setVisibility(View.GONE);

                // oštření, pokud se data nepodařilo načíst
                if (newTradeRecordList == null) {
                    Log.d("JK", "Empty input actual trade list - skipping OnPostExecute");
                    Toast.makeText(getContext(), "Nepodařilo se načíst aktuální data", Toast.LENGTH_LONG).show();
                    return;
                }


                //tradeRecordList = newTradeRecordList;   // toto nefungovalo - adaptér s tím měl problémy
                for (TradeRecord tradeRecord: newTradeRecordList) {
                    tradeRecordList.add(tradeRecord);
                }


                tradesListAdapter.notifyDataSetChanged();

            }
        };
        task.execute("Ahoj");

    }

    @Override
    public void onItemClick(int p) {
     //   Toast.makeText(context, "Vybrána položka " + p , Toast.LENGTH_SHORT).show();

        TradeRecord tradeRecord = tradeRecordList.get(p);        //TODO očetřit aby se správně pracovalo jen s jedním zdrojem dat !!! Array nebo List tradeRecords je zde stejně null
        Gson gson = new GsonBuilder().create();                     // TODO zeptat se kluku, jak co nejefektivněji předávat record do nové aktivity. Možná to ale také vtřeší framy
        String jsonRecordStr = gson.toJson(tradeRecord);


        if (selectedItemListener != null) {
           selectedItemListener.onSelectedItem(p, jsonRecordStr);
        }

    }



}


