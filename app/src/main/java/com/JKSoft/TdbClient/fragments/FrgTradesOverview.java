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

import com.JKSoft.TdbClient.model.TdbRealmDb;
import com.JKSoft.TdbClient.model.data.TradeRecord;
import com.JKSoft.TdbClient.model.TdbDataSource;
import com.JKSoft.TdbClient.ui.recyclerViews.TradesListAdapter;
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

    /**
     * Listener interface for communication from fragment towards main activity
     */
    public interface SelectedItemListener
    {
        /** Selected Item*/
        public void onSelectedItem (Long tradeId);   // TODO - po zprovoznění přes tradeID musí stačit jen TradeID
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

        tradeRecordList = new ArrayList<>();
        context = getContext();

        // RecycleView initialization
        recView = (RecyclerView) view.findViewById(R.id.rvTradesList);
        tradesListAdapter = new TradesListAdapter(tradeRecordList, context);
        recView.setLayoutManager(new LinearLayoutManager(context));
        recView.setItemAnimator(null);
        recView.setAdapter(tradesListAdapter);
        tradesListAdapter.setItemClickCallback(this);  // aby šlo zadat this, musí se implementovat interface

        // Data refresh
        refreshDataFromRealm();   // nyuní jen natažení z cache (Realm)  //TODO prio 1 - aktualizace dat z databáze
        //reloadDataFromSource();  // Původně celý reload  // TODO prio 1 pravidelná aktualizace dat ze serveru
        return view;
    }

    /**
     *  Ceases TraderList from memory and Recycler view
     *  data still remain
     */
    public void clearTradesList() {
        tradeRecordList.clear();
        tradesListAdapter.notifyDataSetChanged();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        selectedItemListener = (SelectedItemListener) context;
    }


    /**
     * Reloads TraderDb data from source (using async task)
     */
    //TODO: probrat s Davidem umístění kódy - zde mi to přijde nepřehledná, ale možná se to jinak udělat nedá
    public void reloadDataFromSource() {

        clearTradesList();
        progressBar.setVisibility(View.VISIBLE  );

        AsyncTask<Void, Void, ArrayList<TradeRecord>> task = new AsyncTask<Void, Void, ArrayList<TradeRecord>>() {
            @Override
            protected ArrayList<TradeRecord> doInBackground(Void... params) {
                return TdbDataSource.getActualTradeRecords(context);
            }

            @Override
            protected void onPostExecute(ArrayList<TradeRecord> newTradeRecordList) {
                super.onPostExecute(newTradeRecordList);
                progressBar.setVisibility(View.GONE);

                // in case the loading data fails
                if (newTradeRecordList == null) {
                    Log.d("JK", "Empty input actual trade list - skipping OnPostExecute");
                    Toast.makeText(getContext(), "Nepodařilo se načíst aktuální data", Toast.LENGTH_LONG).show();
                }

                // Saving data to Realm DB
                TdbRealmDb.deleteAllRealmData();                   // TODO tohle ještě upravit dle čistého MVC - Updatovat databázi a z ní pak view, probrat s Davidem, jeslti je to tak lepší
                TdbRealmDb.saveTradesToRealm_SyncClassic(newTradeRecordList);
                refreshDataFromRealm(); // data refresh in memmry and on UI
          }
        };
        task.execute();
    }

    /**
     * Reads cached data from Realm DB and displays them in UI
     */

    public void refreshDataFromRealm () {

        ArrayList<TradeRecord> newTradeRecords = TdbRealmDb.getAllTradesFromRealm();
        tradeRecordList.clear();
        tradeRecordList.addAll(newTradeRecords);
        tradesListAdapter.notifyDataSetChanged();
    }

    /**
     * Receives RecyclerView position and ID of selected trade and calls registered listener
     * @param p position of item within recycler view
     * @param tradeID Trade Id form Trade Database
     */

    @Override
    public void onItemClick(int p, Long tradeID) {
     //   Toast.makeText(context, "Vybrána položka " + p , Toast.LENGTH_SHORT).show();
        if (selectedItemListener != null) {
           selectedItemListener.onSelectedItem(tradeID);
        }
    }
}


