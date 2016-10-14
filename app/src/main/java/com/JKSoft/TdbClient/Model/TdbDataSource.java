package com.JKSoft.TdbClient.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;


import com.JKSoft.TdbClient.model.structures.RelevantTradesExch;
import com.JKSoft.TdbClient.model.structures.TradeRecord;
import com.JKSoft.TdbClient.rest.adapters.TradersDbRestAdapter;
import com.example.jirka.TdbClient.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.JKSoft.Networking.fms.Ftp.readStringFromFtp;


/**
 * Class ensuring data download from sources like FTP server, mock, TDB REST API
 * Created by Jirka on 27.9.2016.
 */
public class TdbDataSource {
/*  // příprava adaptéru na injection / zatim neimplementovano
    TdbDataSourceListener tdbDataSourceListener = null;
    public interface TdbDataSourceListener {
        public void onDataReceived (int result);
    }
    */

    private static String getJsonActualTradeRecords(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String actualDataSource = sharedPreferences.getString("ACTUAL_DATA_SOURCE", "");
        switch (actualDataSource) {
            case "FTP":
                return readStringFromFtp("/FilesDB/RelevantTrades.json"); // ftp source
            case "MOCK":
                return readStringFromResources(R.raw.relevant_trades_mock, context);  // Mock data
            case "TDB_SERVER":                                                                  // TODO: přepsat tak, aby se vracel rovnou RelevantTradesExch pro všechny typy přístupů
                return readStringFromTdbServer();
            default:
                return "";
        }
    }

    private static String readStringFromTdbServer() {
        TradersDbRestAdapter restAdapter = new TradersDbRestAdapter();
        RelevantTradesExch relevantTradesExch =restAdapter.getActualTrades();
        Gson gson = new GsonBuilder().create();
        String jsonStr = gson.toJson(relevantTradesExch);
        return jsonStr;
    }


    private static String readStringFromResources (int resID, Context context) {
        Resources resources = context.getResources();
        InputStream inputStream = resources.openRawResource(resID);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


        StringBuffer jsonStrBuf = new StringBuffer(1024);
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                jsonStrBuf.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStrBuf.toString();

    }

    /**
     * Downloads actual data from source defined in preferences
     * @param context - currently needed for getting data from resources - will be implemented using mock data
     * @return  list of TradeRecords
     */

    @Nullable
    public static RelevantTradesExch downloadActualRelevantTradesExch(Context context) {   // TODO: probrat s Davidem - vymyslet jinak mock data - context předávám  jen kvůli přístupu do resources, to je špatně, implementovat jinak

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String actualDataSource = sharedPreferences.getString("ACTUAL_DATA_SOURCE", "");
        RelevantTradesExch relevantTradesExch = null;

        switch (actualDataSource) {
            case "FTP":
                String jsonString = readStringFromFtp("/FilesDB/RelevantTrades.json"); // ftp source
                if (jsonString.startsWith("error"))  {
                    Log.e ("JK", jsonString);
                } else {
                    relevantTradesExch = jsonToRelevantTradesExch(jsonString);
                }
                break;
            case "MOCK":
                String jsonString2 = readStringFromResources(R.raw.relevant_trades_mock, context);  // Mock data
                relevantTradesExch = jsonToRelevantTradesExch(jsonString2);
                break;
            case "TDB_SERVER":                                                                  // TODO: přepsat tak, aby se vracel rovnou RelevantTradesExch pro všechny typy přístupů
                TradersDbRestAdapter restAdapter = new TradersDbRestAdapter();
                relevantTradesExch =restAdapter.getActualTrades();
                break;
            default:
                break;
        }
        return relevantTradesExch;
    }

    @Nullable
    private static RelevantTradesExch jsonToRelevantTradesExch (String jsonString) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonString, RelevantTradesExch.class);
    }

    @Nullable
    public static ArrayList<TradeRecord> getActualTradeRecords(Context context) {   // TODO David vymyslet jinak mock data - context předávám  jen kvůli přístupu do resources, to je špatně, implementovat jinak
        RelevantTradesExch relevantTradesExch = downloadActualRelevantTradesExch(context);
        if (null != relevantTradesExch) {
            return relevantTradesExch.getTrades();
        } else {
            return null;
        }
    }

    /**
     * Will be used later when implementing Realm update as a service
     * @param context
     * @return
     */
    public static int loadActualTradeRecordsToRealm(Context context) {   // TODO David vymyslet jinak mock data - context předávám  jen kvůli přístupu do resources, to je špatně

        ArrayList<TradeRecord> tradeRecords = getActualTradeRecords(context);
        TdbRealmDb.deleteAllRealmData();
        TdbRealmDb.saveTradesToRealm_SyncClassic(tradeRecords);       // TODO předělat na async, jestli bude rozumné
        return tradeRecords.size();     // TODO - správně by měl vrátit počet uložených do Realm
    }

}

