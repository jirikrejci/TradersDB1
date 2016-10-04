package com.JKSoft.TdbClient.Model;

import android.content.Context;
import android.util.Log;

import com.JKSoft.TdbClient.dataStructures.TradeRecord;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Jirka on 4.10.2016.
 */
public class TdbRealm {

    public static final String TAG = "Tdb-TdbRealm";

    Context context;

//    public TdbRealm (Context context) {
//        this.context = context;
//    }


    public static void saveTradesToRealm_SyncClassic (ArrayList<TradeRecord> tradeRecords) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        for (TradeRecord tradeRecord: tradeRecords) {
            TradeRecord tradeRecordStored = realm.copyToRealm(tradeRecord);
        }
        realm.commitTransaction();
    }

    public static void saveTradesToRealm_SyncTransactionBlock (final ArrayList<TradeRecord> tradeRecords) {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (TradeRecord tradeRecord:tradeRecords) {
                    TradeRecord tradeRecordStored = realm.copyToRealm(tradeRecord);
                }
            }
        });
    }

    public static void staveTradesToRealm_Async (final ArrayList<TradeRecord> tradeRecords) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
                                          @Override
                                          public void execute(Realm bgrealm) {
                                              for (TradeRecord tradeRecord : tradeRecords) {
                                                  TradeRecord tradeRecordStored = realm.copyToRealm(tradeRecord);
                                              }
                                          }
                                      }, new Realm.Transaction.OnSuccess() {
                                          @Override
                                          public void onSuccess() {
                                              Log.d(TAG, "saveToRealm Assync - succesfully finished");


                                          }
                                      }, new Realm.Transaction.OnError() {
                                          @Override
                                          public void onError(Throwable error) {
                                              Log.e(TAG, "saveTradesToRealm_Async error:" + error.toString());
                                          }
                                      }




        );
    }



    public static ArrayList<TradeRecord> getAllTradesFromRealm () {
        Realm realm = Realm.getDefaultInstance();
        ArrayList <TradeRecord> tradeRecords = new ArrayList<TradeRecord>();

        RealmQuery<TradeRecord> realmQuery = realm.where(TradeRecord.class);
        RealmResults<TradeRecord> realmResults = realmQuery.findAll();

        for (int i = 0; i< realmResults.size(); i++) {
            tradeRecords.add(realmResults.get(i));
        }


        return tradeRecords;

    }

    public static TradeRecord getTradeFromRealm (int tradeId) {
        Realm realm = Realm.getDefaultInstance();
        ArrayList <TradeRecord> tradeRecords = new ArrayList<TradeRecord>();

        RealmQuery<TradeRecord> realmQuery = realm.where(TradeRecord.class)
                .equalTo("tradeId", tradeId);
        RealmResults<TradeRecord> realmResults = realmQuery.findAll();

        if (realmResults.size() == 0) {
            Log.e("TDB", "No trade with id " + tradeId + " in database");
            return null;
        } else {

            if (realmResults.size() > 1 ) Log.e("TDB", "Waring - more than one trade with  tradeId: " + tradeId);
            return (TradeRecord) realmResults.get(0);
        }
    }


    public static ArrayList<TradeRecord> getAllPendingTradesFromRealm () {
        Realm realm = Realm.getDefaultInstance();
        ArrayList <TradeRecord> tradeRecords = new ArrayList<TradeRecord>();

        RealmQuery<TradeRecord> realmQuery = realm.where(TradeRecord.class)
                .equalTo("orderStatus", "pending");
        RealmResults<TradeRecord> realmResults = realmQuery.findAll();

        for (int i = 0; i< realmResults.size(); i++) {
            tradeRecords.add(realmResults.get(i));
        }


        return tradeRecords;

    }

    public static void deleteAllRealmData() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
    }




    public static void realmTests (Context context) {
        ArrayList<TradeRecord> tradeRecords = TdbDataSource.getActualTradeRecords(context);
        saveTradesToRealm_SyncClassic(tradeRecords);

        ArrayList<TradeRecord> allTradesList = getAllTradesFromRealm(); Log.d(TAG, "allTradeList size = " + allTradesList.size());
        ArrayList<TradeRecord> pendingTradesList = getAllPendingTradesFromRealm(); Log.d(TAG, "pendingTradeList size = " + pendingTradesList.size());
        int id = 2968;
        TradeRecord actualTradeRecord = getTradeFromRealm(id);
        if (null != actualTradeRecord)
            Log.d(TAG, "actualTradeRecord (searched id_" + id + " = " + actualTradeRecord.toString()) ;
        else
            Log.d(TAG, "actualTradeRecord (searched id_" + id + "= null") ;



    }




}
