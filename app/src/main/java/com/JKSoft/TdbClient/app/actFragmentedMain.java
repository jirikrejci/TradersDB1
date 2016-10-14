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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.JKSoft.TdbClient.fragments.FrgTradeDetail;
import com.JKSoft.TdbClient.fragments.FrgTradesOverview;
import com.JKSoft.TdbClient.model.TdbRealmDb;
import com.crashlytics.android.Crashlytics;
import com.example.jirka.TdbClient.R;
import com.jakewharton.scalpel.ScalpelFrameLayout;

import butterknife.BindView;
import io.fabric.sdk.android.Fabric;

/**
 * Main Activity managing application behaviour
 * in Landscape mode activity holds two fragments - with overview and detail
 * in Portrait mode activity holds fragment with overview and launches a new activity holding fragment with detail
 * Created by Jirka on 3.10.2016.
 */
// TODO: zeptat se Davida, jesli logiks popsaná výše je správně, jeslti by se např. v portrait modu neměly vyměňovat fragmenty

public class ActFragmentedMain extends AppCompatActivity implements FrgTradesOverview.SelectedItemListener {

    FrgTradesOverview frgTradesOverview;
    FrgTradeDetail frgTradeDetail;
    Boolean twoFragmentsUsed;   // true if two frgments mode is applied (landscape mode)
    //  @InjectView(R.id.scalpel) ScalpelFrameLayout scalpelView;

    @BindView(R.id.scalpel)
    ScalpelFrameLayout scalpelView;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (event.getAction()) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                Toast.makeText(this, "Volume key down", Toast.LENGTH_SHORT).show();
                scalpelView.setLayerInteractionEnabled(!scalpelView.isLayerInteractionEnabled());
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.act_trades_overview_fragmented);

        // inserting TradesOverviewFragment
        frgTradesOverview = new FrgTradesOverview();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frgContainerTradesOverview, frgTradesOverview);
        fragmentTransaction.commit();

        // insert detail fragment if two fragments shall be used
        twoFragmentsUsed = getUseTwoFragments();
        if (twoFragmentsUsed) {
            frgTradeDetail = new FrgTradeDetail();
            fragmentManager.beginTransaction()
                    .replace(R.id.frgContainerTradesDetail, frgTradeDetail)
                    .commit();
        }
    }

    private boolean getUseTwoFragments (){
        return (null != findViewById(R.id.frgContainerTradesDetail));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.mReloadDataFromServer:
                frgTradesOverview.reloadDataFromSource();  // data reload from network source
                break;
            case R.id.mReloadDataFromRealm:         // data reload from internal realm DB
                frgTradesOverview.refreshDataFromRealm();
                break;
            /*case R.id.mReadDataFromFtp:             // not implemented - force reload data from FTP
                Toast.makeText(this, "JK: Reload data requested - not implemented yet", Toast.LENGTH_LONG).show();
                break;*/
            case R.id.mDeleteDataFromRealm:         // deleting data from internal realm DB
                TdbRealmDb.deleteAllRealmData();
                break;
            case R.id.mWipeDataFromMemory:  // deleting data from list - data still stored in Realm DB
                frgTradesOverview.clearTradesList();
                Toast.makeText(this, "Wipe data from memory DONE", Toast.LENGTH_LONG).show();
                break;
            /* case R.id.mRealmTests:          // not implemented
                //TdbRealmDb.realmTests(this);
                break;*/
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

    @Override
    public void onSelectedItem(Long tradeId) {
        //For debugging: Toast.makeText(this, "Položka " + p + "přijata v hlavní aktivitě", Toast.LENGTH_SHORT).show();
        if (twoFragmentsUsed) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FrgTradeDetail frgTradeDetail = (FrgTradeDetail) fragmentManager.findFragmentById(R.id.frgContainerTradesDetail);
            frgTradeDetail.displayTradeRecord(tradeId);
        } else {
            Intent intent = new Intent(this, ActTradeDetail.class);
            intent.putExtra(FrgTradeDetail.SELECTED_RECORD_TRADE_ID, tradeId);
            startActivity(intent);
        }
    }

    private void showSettingsFragment() {
        Intent intent = new Intent(this, ActPreferences.class );
        startActivity(intent);
    }
}


