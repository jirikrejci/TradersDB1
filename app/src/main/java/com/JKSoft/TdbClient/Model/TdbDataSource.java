package com.JKSoft.TdbClient.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Log;

import com.JKSoft.Networking.fms.Ftp;
import com.JKSoft.TdbClient.dataStructures.RelevantTradesExch;
import com.JKSoft.TdbClient.dataStructures.TradeRecord;
import com.example.jirka.TdbClient.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Jirka on 27.9.2016.
 */
public class TdbDataSource {
/*  // příprava adaptéru na injection - možná ne
    TdbDataSourceListener tdbDataSourceListener = null;
    public interface TdbDataSourceListener {
        public void onDataReceived (int result);
    }
    */

    public static String getJsonActualTradeRecords(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String actualDataSource = sharedPreferences.getString("ACTUAL_DATA_SOURCE", "");
        switch (actualDataSource) {
            case "FTP":
                return Ftp.readStringFromFtp("/FilesDB/RelevantTrades.json"); // ftp source
            case "MOCK":
                return readStringFromResources(R.raw.relevant_trades_mock, context);  // Mock data
            case "TDB_SERVER":
                return "error: TDB not implemented yet";
            default:
                return "";
        }
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




    public static ArrayList<TradeRecord> getActualTradeRecords(Context context) {   // TODO David vymyslet jinak mock data - context předávám  jen kvůli přístupu do resources, to je špatně


        String jsonString = getJsonActualTradeRecords(context);
        if (jsonString.startsWith("error"))  {
            Log.e("JK", jsonString);

            return null;
        }

        Gson gson = new GsonBuilder().create();
        RelevantTradesExch relevantTradesExch = gson.fromJson(jsonString, RelevantTradesExch.class);
        return relevantTradesExch.getTrades();

    }

    public static int loadActualTradeRecordsToRealm(Context context) {   // TODO David vymyslet jinak mock data - context předávám  jen kvůli přístupu do resources, to je špatně

        ArrayList<TradeRecord> tradeRecords = getActualTradeRecords(context);
        TdbRealm.deleteAllRealmData();
        TdbRealm.saveTradesToRealm_SyncClassic(tradeRecords);       // TODO předělat na async, jestli bude rozumné
        return tradeRecords.size();     // TODO - správně by měl vrátit počet uložených do Realm
    }


}
