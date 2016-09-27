package com.JKSoft.TdbClient.Model;

import com.JKSoft.DataStructures.RelevantTradesExch;
import com.JKSoft.DataStructures.TradeRecord;
import com.JKSoft.Networking.fms.Ftp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    public static String getJsonActualTradeRecords() {
        return Ftp.readStringFromFtp("/FilesDB/RelevantTrades.json");
    }


    public static ArrayList<TradeRecord> getActualTradeRecords() {

        String jsonString = getJsonActualTradeRecords();

        Gson gson = new GsonBuilder().create();
        RelevantTradesExch relevantTradesExch = gson.fromJson(jsonString, RelevantTradesExch.class);
        TradeRecord[] tradeRecords = relevantTradesExch.getTrades();

        ArrayList <TradeRecord> tradeRecordList = new ArrayList<TradeRecord> ();
        for (int i = 0; i< tradeRecords.length; i++) {              // TODO pokud nepůjde, alespoň předělat na for each - nebo rovnou předělat, ať se něco nauíčm
            tradeRecordList.add(tradeRecords[i]);
        }

        return tradeRecordList;
    }






}
