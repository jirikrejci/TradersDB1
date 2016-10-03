package com.JKSoft.TdbClient.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.JKSoft.DataStructures.TradeRecord;
import com.JKSoft.TdbClient.fragments.FrgTradeDetail;
import com.JKSoft.TdbClient.fragments.FrgTradesOverview;
import com.example.jirka.TdbClient.R;

import java.util.List;

public class actFragmentedMain extends AppCompatActivity implements FrgTradesOverview.SelectedItemListener {

    RecyclerView recView;
    FrgTradesOverview frgTradesOverview;
    FrgTradeDetail frgTradeDetail;
    Boolean twoFragments;

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
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        frgTradesOverview = new FrgTradesOverview();
        fragmentTransaction.replace(R.id.frgContainerTradesOverview, frgTradesOverview);
        //fragmentTransaction.add(R.id.frgContainerTradesOverview, frgTradesOverview);
        fragmentTransaction.commit();

        if (null == findViewById(R.id.frgContainerTradesDetail)) {
            twoFragments = false;
        } else {
            twoFragments = true;

            frgTradeDetail = new FrgTradeDetail();

            fragmentManager.beginTransaction()
                    .replace(R.id.frgContainerTradesDetail, frgTradeDetail)
                    .commit();
        }






        //frgTradesOverview = (FrgTradesOverview)  findViewById(R.id.frgTradesOverview);
      //  ((FrgTradesOverview) findViewById(R.id.frgTradesOverview)).setSelectedItemListener(this.onSelectedItem());
      //  frgTradeDetail = (FrgTradeDetail) findViewById(R.id.frgTradesDetail2);

       // frgTradesOverview.reloadData();

    }




    //TODO - projít Styles and themes guide  https://developer.android.com/guide/topics/ui/themes.html


/*
    @Override

    public void onItemClick(int p) {
        Intent intent = new Intent(this, actTradeDetail_old.class);
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
                frgTradesOverview.reloadData();
                break;
            case R.id.mReadDataFromFtp:
                Toast.makeText(this, "JK: Reload data requested", Toast.LENGTH_LONG).show();
                break;
            case R.id.mWipeDataFromMemory:
                Toast.makeText(this, "JK: Wipe data from memory", Toast.LENGTH_LONG).show();
                break;
            case R.id.mAbout:
                aboutMenuItem();
                break;
            case R.id.mSettings:
                showSettingsFragment();
                break;
            default:
                Toast.makeText(this, "\"" + item.getTitle() + "\" menu item selected", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }


    private void aboutMenuItem() {
        Resources res = getResources();
        String strVersion = "App name:" +res.getString(R.string.app_name) +
                "\nBild type: " + res.getString(R.string.build_type) +
                "\nFlavour version: " + res.getString(R.string.flavour_version);

        new AlertDialog.Builder(this)
                .setTitle("About")
                .setMessage("Trader DB by Jiri Krejci C\n" + strVersion )
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
        if (twoFragments) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FrgTradeDetail frgTradeDetail = (FrgTradeDetail) fragmentManager.findFragmentById(R.id.frgContainerTradesDetail);
            frgTradeDetail.displayTradeRecordJson(jsonItem);
        } else {
            Intent intent = new Intent(this, actTradeDetail.class);
            intent.putExtra(FrgTradeDetail.SELECTED_RECORD_JSON, jsonItem);
            intent.putExtra(FrgTradeDetail.SELECTED_ITEM_POS, p);
            startActivity(intent);
        }


/*
        // verze s vytvořením nové instance a prohozením
        FrgTradeDetail frgTradeDetail = FrgTradeDetail.newInstance(p, jsonItem);
        FragmentTransaction frgt = getSupportFragmentManager().beginTransaction();
        frgt.replace(R.id.frgTradesDetail2, frgTradeDetail);
        frgt.addToBackStack(null);
        frgt.commit();
        */

    }




    private void showSettingsFragment() {
        Intent intent = new Intent(this, actPreferences.class );
        startActivity(intent);
    }


}

