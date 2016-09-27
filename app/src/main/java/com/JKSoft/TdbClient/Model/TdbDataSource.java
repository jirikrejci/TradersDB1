package com.JKSoft.TdbClient.Model;

import android.content.Context;
import android.content.res.Resources;

import com.JKSoft.DataStructures.RelevantTradesExch;
import com.JKSoft.DataStructures.TradeRecord;
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

        return readStringFromResources(R.raw.relevant_trades_mock, context);  // TODO udělat přepínání zdrojů dat - buď setupem v aplikaci, nebo if podle flavour a build type
       // return Ftp.readStringFromFtp("/FilesDB/RelevantTrades.json");  // ftp source
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

        Gson gson = new GsonBuilder().create();
        RelevantTradesExch relevantTradesExch = gson.fromJson(jsonString, RelevantTradesExch.class);
        TradeRecord[] tradeRecords = relevantTradesExch.getTrades();

        ArrayList <TradeRecord> tradeRecordList = new ArrayList<TradeRecord> ();
        for (TradeRecord tradeRecord: tradeRecords) {              // TODO zkusit, jestli gson neumí rovnou nahrát datata do ArrayList
            tradeRecordList.add(tradeRecord);
        }



        return tradeRecordList;
    }






}
