package com.JKSoft.TdbClient.model;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.JKSoft.TdbClient.model.data.TradeRecord;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 *Class managing internal database implemented via Realm
 * Created by Jirka on 4.10.2016.
 */
public class TdbRealmDb {

    private static final String TAG = "Tdb-TdbRealmDb";

/*   singleton example - not used in final app yet
    private static Realm sRealm = null;

    protected void TdbRealm() {
        // Exists only to defeat instantiation
    }

    public static Realm getRealmDb () {
        if (null == sRealm) {
            sRealm = Realm.getDefaultInstance();
        }
        return sRealm;
    }

    public static void closeDb () {
        if (null != sRealm) {
            sRealm.close();
            sRealm = null;
            Log.d(TAG, "TdbRealmDb database closed");
        }
    }

*/


    public static void saveTradesToRealm_SyncClassic(ArrayList<TradeRecord> tradeRecords) {
        Realm db = Realm.getDefaultInstance();
        db.beginTransaction();
        for (TradeRecord tradeRecord : tradeRecords) {
            db.copyToRealm(tradeRecord);
        }
        db.commitTransaction();
        db.close();
    }


    /**
     * Returns arr trades from Realm DB (no internal filter applied)
     *
     * @return list of Trades
     */

    public static ArrayList<TradeRecord> getAllTradesFromRealm() {
        Realm db = Realm.getDefaultInstance();
        ArrayList<TradeRecord> tradeRecords = new ArrayList<TradeRecord>();

        RealmQuery<TradeRecord> realmQuery = db.where(TradeRecord.class);
        RealmResults<TradeRecord> realmResults = realmQuery.findAll();

        tradeRecords.addAll(realmResults);
        db.close();
        return tradeRecords;
    }


    /**
     * Returns TradeRecord with particular tradeID from Realm DB
     *
     * @param tradeId - ID of trade in Trader DB
     * @return Traderecord
     */

    @Nullable
    public static TradeRecord getTradeFromRealm(Long tradeId) {
        Realm db = Realm.getDefaultInstance();

        RealmQuery<TradeRecord> realmQuery = db
                .where(TradeRecord.class)
                .equalTo("tradeId", tradeId);
        RealmResults<TradeRecord> realmResults = realmQuery.findAll();

        if (realmResults.size() == 0) {
            Log.d("TDB", "No trade with id " + tradeId + " in database");
            return null;
        } else {
            if (realmResults.size() > 1) {
                Log.d("TDB", "Waring - more than one trade with  tradeId: " + tradeId);
            }
            return realmResults.get(0);
        }
    }

    /**
     * Extracts all Trades with status PENDING from Realm DB
     *
     * @return list of TradereCord
     */

    public static ArrayList<TradeRecord> getAllPendingTradesFromRealm() {
        ArrayList<TradeRecord> tradeRecords = new ArrayList<TradeRecord>();

        Realm db = Realm.getDefaultInstance();

        RealmQuery<TradeRecord> realmQuery = db.where(TradeRecord.class)
                .equalTo("orderStatus", "pending");
        RealmResults<TradeRecord> realmResults = realmQuery.findAll();

        tradeRecords.addAll(realmResults);

        db.close();
        return tradeRecords;
    }

    public static void deleteAllRealmData() {
        Realm db = Realm.getDefaultInstance();
        db.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
        db.close();
    }


    // Various types of transacction used for investigation purposes (not used in final app) follow
    public static void saveTradesToRealm_SyncTransactionBlock(final ArrayList<TradeRecord> tradeRecords) {
        Realm db = Realm.getDefaultInstance();
        db.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (TradeRecord tradeRecord : tradeRecords) {
                    realm.copyToRealm(tradeRecord);
                }
            }
        });
        db.close();
    }

    public static void staveTradesToRealm_Async(final ArrayList<TradeRecord> tradeRecords) {
        Realm db = Realm.getDefaultInstance();

        db.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgrealm) {
                        for (TradeRecord tradeRecord : tradeRecords) {
                            bgrealm.copyToRealm(tradeRecord);
                        }
                    }
                },
                new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "saveToRealm Assync - succesfully finished");
                    }
                },
                new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Log.e(TAG, "saveTradesToRealm_Async error:" + error.toString());
                    }
                }
        );

        db.close();
    }
}