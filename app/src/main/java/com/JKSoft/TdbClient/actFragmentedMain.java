package com.JKSoft.TdbClient;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.JKSoft.DataStructures.TradeRecord;
import com.JKSoft.TdbClient.TradesRecyclerView.adapter.TradesListAdapter;
import com.example.jirka.TdbClient.R;

import java.util.List;

public class actFragmentedMain extends AppCompatActivity implements FrgTradesOverview.SelectedItemListener {

    RecyclerView recView;
    TradesListAdapter tradesListAdapter;
    FrgTradesOverview frgTradesOverview;
    FrgTradeDetail frgTradeDetail;

    List<TradeRecord> tradeRecordList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_trades_overview_fragmented);

        //frgTradesOverview = (FrgTradesOverview)  findViewById(R.id.frgTradesOverview);
      //  ((FrgTradesOverview) findViewById(R.id.frgTradesOverview)).setSelectedItemListener(this.onSelectedItem());

      //  frgTradeDetail = (FrgTradeDetail) findViewById(R.id.frgTradesDetail2);

    }


/*
    public void btnReloadData_OnClick(View view) {

        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return Ftp.readStringFromFtp("/FilesDB/RelevantTrades.json");  // OK
            }

            @Override
            protected void onPostExecute(String jsonString) {
                super.onPostExecute(jsonString);
                Gson gson = new GsonBuilder().create();
                RelevantTradesExch relevantTradesExch = gson.fromJson(jsonString, RelevantTradesExch.class);
                TradeRecord[] tradeRecords = relevantTradesExch.getTrades();

                tradeRecordList.clear();
                for (int i = 0; i< tradeRecords.length; i++) {              // TODO pokud nepůjde, alespoˇn předělat na for each - nebo rovnou předělat, ať se něco nauíčm
                    tradeRecordList.add(tradeRecords[i]);
                }
                tradesListAdapter.notifyDataSetChanged();
         /*       RelevantTradesExch tradesExch;
                Gson gson = new GsonBuilder().create();
                tradesExch = gson.fromJson(text,RelevantTradesExch.class);

            }
        };
        task.execute("Ahoj");

    }
*/
/*
    @Override

    public void onItemClick(int p) {
        Intent intent = new Intent(this, actTradeDetail.class);
        TradeRecord tradeRecord = tradeRecordList.get(p);        //TODO očetřit aby se správně pracovalo jen s jedním zdrojem dat !!! Array nebo List tradeRecords je zde stejně null
        Gson gson = new GsonBuilder().create();                     // TODO zeptat se kluku, jak co nejefektivněji předávat record do nové aktivity. Možná to ale také vtřeší framy
        String jsonRecordStr = gson.toJson(tradeRecord);
        intent.putExtra("TRADE_RECORD", jsonRecordStr);
        startActivity(intent);
    }
    */

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

    @Override           // TODO nezdá se mi myměňování framů. Nejdou data prostě jen updatovat?
    public void onSelectedItem(int p, String jsonItem) {
        //Toast.makeText(this, "Položka " + p + "přijata v hlavní aktivitě", Toast.LENGTH_SHORT).show();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FrgTradeDetail frgTradeDetail = (FrgTradeDetail) fragmentManager.findFragmentById(R.id.frgTradesDetail2);

        frgTradeDetail.displayTradeRecordJson(jsonItem);

/*
        // verze s vytvořením nové instance a prohozením
        FrgTradeDetail frgTradeDetail = FrgTradeDetail.newInstance(p, jsonItem);
        FragmentTransaction frgt = getSupportFragmentManager().beginTransaction();
        frgt.replace(R.id.frgTradesDetail2, frgTradeDetail);
        frgt.addToBackStack(null);
        frgt.commit();
        */

    }
}


