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

import com.JKSoft.TdbClient.Model.TdbRealm;
import com.JKSoft.TdbClient.dataStructures.TradeRecord;
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
        public void onSelectedItem (int p, String itemJson, Long tradeId);   // TODO - po zprovoznění přes tradeID musí stačit jen TradeID
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
        //TODO D move to onViewCreated method
        ButterKnife.bind(this, view);

        tradeRecordList = new ArrayList<>();    // TODO předělat podle příkladu

        context = getContext();

        // RecycleView
        //TODO D convert to ButterKnife
        recView = (RecyclerView) view.findViewById(R.id.rvTradesList);
        tradesListAdapter = new TradesListAdapter(tradeRecordList, context);
        recView.setLayoutManager(new LinearLayoutManager(context));
        recView.setItemAnimator(null);
        recView.setAdapter(tradesListAdapter);

        tradesListAdapter.setItemClickCallback(this);  // aby šlo zadat this, musí se implementovat interface


        // natavení listeneru pro click v seznamu

       //reloadDataFromSource();  // Původní celý reload  // TODO prio 1 pravidelná aktualizace dat ze serveru
        refreshDataFromRealm();   // nyuní jen natažení z cache   //TODO prio 1 - aktualizace dat z databáze

        return view;
    }

    public void clearTradesList() {
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


   

    public void reloadDataFromSource() {

        clearTradesList();
        progressBar.setVisibility(View.VISIBLE  );

        //TODO D loading outside fragment
        AsyncTask<String, Void, ArrayList<TradeRecord>> task = new AsyncTask<String, Void, ArrayList<TradeRecord>>() {
            @Override
            protected ArrayList<TradeRecord> doInBackground(String... strings) {
                //TODO D check orientation change
//                try {
//                    Thread.sleep(5000L);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                return TdbDataSource.getActualTradeRecords(context);
            }

            @Override
            protected void onPostExecute(ArrayList<TradeRecord> newTradeRecordList) {
                super.onPostExecute(newTradeRecordList);
                progressBar.setVisibility(View.GONE);

                // oštření, pokud se data nepodařilo načíst
                if (newTradeRecordList == null) {
                    Log.d("JK", "Empty input actual trade list - skipping OnPostExecute");
                    Toast.makeText(getContext(), "Nepodařilo se načíst aktuální data", Toast.LENGTH_LONG).show();
                    return;
                }

                //tradeRecordList = newTradeRecordList;   // toto nefungovalo - adaptér s tím měl problémy
                tradeRecordList.clear();

//            TODO D    tradeRecordList.addAll(newTradeRecordList);
                for (TradeRecord tradeRecord: newTradeRecordList) {
                    tradeRecordList.add(tradeRecord);
                }
                tradesListAdapter.notifyDataSetChanged();

                // ukládání do Realm
               TdbRealm.deleteAllRealmData();                   // TODO tohle ještě upravit dle čistého MVC - Updatovat databázi a z ní pak view
               TdbRealm.saveTradesToRealm_SyncClassic(newTradeRecordList);

            }
        };
        task.execute("Ahoj");

    }


    public void refreshDataFromRealm () {

        clearTradesList();
        ArrayList<TradeRecord> newTradeRecords =TdbRealm.getAllTradesFromRealm();
        for (TradeRecord tradeRecord: newTradeRecords) {
            tradeRecordList.add(tradeRecord);   // TODO AddALL
        }
        tradesListAdapter.notifyDataSetChanged();
    }




    @Override
    public void onItemClick(int p, Long tradeID) {
     //   Toast.makeText(context, "Vybrána položka " + p , Toast.LENGTH_SHORT).show();



        TradeRecord tradeRecord = tradeRecordList.get(p);        //TODO očetřit aby se správně pracovalo jen s jedním zdrojem dat !!! Array nebo List tradeRecords je zde stejně null
        Gson gson = new GsonBuilder().create();                     // TODO zeptat se kluku, jak co nejefektivněji předávat record do nové aktivity. Možná to ale také vtřeší framy
        //String jsonRecordStr = gson.toJson(tradeRecord);  // TODO proi 1 vymyslet, aby mi gson fungoval pod Realmem
        String jsonRecordStr = "dummy";




        if (selectedItemListener != null) {
           selectedItemListener.onSelectedItem(p, jsonRecordStr, tradeID);
        }

    }



}


