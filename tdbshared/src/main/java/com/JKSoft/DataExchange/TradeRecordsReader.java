package com.JKSoft.DataExchange;

import com.JKSoft.DataStructures.RelevantTradesExch;
import com.JKSoft.Methods.JsonConversions;
import com.JKSoft.Networking.NetworkFms.Ftp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Jirka on 25.8.2016.
 */
public class TradeRecordsReader {
    public static RelevantTradesExch readTradeRecors () {
        String jsonStr;
        jsonStr = Ftp.readStringFromFtp("/FilesDB/RelevantTrades.json");  // OK
       //jsonStr = ResourcesManager.readResourceToString("/resources/resource_example.json");

        Gson gson = new GsonBuilder().create();
        RelevantTradesExch relevantTradesExch = gson.fromJson(jsonStr, RelevantTradesExch.class);
        System.out.println("Json:\n" + JsonConversions.getJsonPretty(relevantTradesExch));
        System.out.println(relevantTradesExch.toString());

        return relevantTradesExch;
    }
}
