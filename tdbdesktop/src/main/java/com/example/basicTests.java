package com.example;

import com.JKSoft.DataStructures.RelevantTradesExch;
import com.JKSoft.Networking.fms.Ftp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Jirka on 16.9.2016.
 */
public class    basicTests {
    public static void main (String [] args) {
        String inputStr;
        String jsonPrettyStr;

        inputStr = Ftp.readStringFromFtp("/FilesDB/RelevantTrades.json"); System.out.println(inputStr); // OK
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        RelevantTradesExch relevantTradesExch = gson.fromJson(inputStr, RelevantTradesExch.class);
        jsonPrettyStr = gson.toJson(relevantTradesExch);
        Ftp.writeStringToFtp("/FilesDB/RelevantTradesPretty.json", jsonPrettyStr);

    }
}
