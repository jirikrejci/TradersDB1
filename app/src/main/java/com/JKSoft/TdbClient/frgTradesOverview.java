package com.JKSoft.TdbClient;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.JKSoft.DataStructures.RelevantTradesExch;
import com.JKSoft.DataStructures.TradeRecord;
import com.JKSoft.TdbClient.Model.TdbDataSource;
import com.JKSoft.TdbClient.TradesRecyclerView.adapter.TradesListAdapter;
import com.example.jirka.TdbClient.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class FrgTradesOverview extends Fragment implements TradesListAdapter.ItemClickCallback{

    RecyclerView recView;
    TradesListAdapter tradesListAdapter;
    List<TradeRecord> tradeRecordList;
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
        View view = inflater.inflate(R.layout.act_trades_overview, container);
        tradeRecordList = new ArrayList<>();    // TODO předělat podle příkladu

        context = getContext();

        // RecycleView
        recView = (RecyclerView) view.findViewById(R.id.rvTradesList);
        tradesListAdapter = new TradesListAdapter(tradeRecordList, context);
        recView.setLayoutManager(new LinearLayoutManager(context));
        recView.setAdapter(tradesListAdapter);
        tradesListAdapter.setItemClickCallback(this);  // aby šlo zadat this, musí se implementovat interface

        //Button
        Button btnReloadData = (Button) view.findViewById(R.id.btnReloadData);   // TODO zeptat se davida, jek při vybírání ID vidět je na, která jsou v kontextu daného layoutu - dost se mi to plete, co když buodu v e dvou layoutech stejná ID?
        btnReloadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadData();
            }
        });

        // natavení listeneru pro click v seznamu



        return view;
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

        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return TdbDataSource.getJsonActualTradeRecords();  // OK
            }

            @Override
            protected void onPostExecute(String jsonString) {
                super.onPostExecute(jsonString);
                Gson gson = new GsonBuilder().create();
                RelevantTradesExch relevantTradesExch = gson.fromJson(jsonString, RelevantTradesExch.class);
                TradeRecord[] tradeRecords = relevantTradesExch.getTrades();

                tradeRecordList.clear();
                for (int i = 0; i< tradeRecords.length; i++) {              // TODO pokud nepůjde, alespoň předělat na for each - nebo rovnou předělat, ať se něco nauíčm
                    tradeRecordList.add(tradeRecords[i]);
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


    public void reloadData2 () {


        AsyncTask<String, Void, List<TradeRecord>> task = new AsyncTask<String, Void, List<TradeRecord>>() {
            @Override
            protected ArrayList<TradeRecord> doInBackground(String... strings) {
                return TdbDataSource.getActualTradeRecords();
            }

            @Override
            protected void onPostExecute(List<TradeRecord> newTradeRecordList) {
                super.onPostExecute(tradeRecordList);
                Gson gson = new GsonBuilder().create();
//                RelevantTradesExch relevantTradesExch = gson.fromJson(jsonString, RelevantTradesExch.class);
  //              TradeRecord[] tradeRecords = relevantTradesExch.getTrades();

          //      tradeRecordList.clear();
    //            for (int i = 0; i< tradeRecords.length; i++) {              // TODO pokud nepůjde, alespoň předělat na for each - nebo rovnou předělat, ať se něco nauíčm
      //              tradeRecordList.add(tradeRecords[i]);


                tradeRecordList = newTradeRecordList;
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
     //   Toast.makeText(context, "Vybrána položka " + p , Toast.LENGTH_SHORT).show();

        TradeRecord tradeRecord = tradeRecordList.get(p);        //TODO očetřit aby se správně pracovalo jen s jedním zdrojem dat !!! Array nebo List tradeRecords je zde stejně null
        Gson gson = new GsonBuilder().create();                     // TODO zeptat se kluku, jak co nejefektivněji předávat record do nové aktivity. Možná to ale také vtřeší framy
        String jsonRecordStr = gson.toJson(tradeRecord);


        if (selectedItemListener != null) {
           selectedItemListener.onSelectedItem(p, jsonRecordStr);
        }

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


    /* TODO zprovoznit menu
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
    */
}


