package com.JKSoft.TdbClient.dataStructures;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Jirka on 28.8.2016.
 */
public class RelevantTradesExch {
    //TODO D private fields
    @SerializedName("date created")
    String date;

    @SerializedName("protocol version")
    String protocolVersion;

    ArrayList<TradeRecord> trades;


    public ArrayList <TradeRecord> getTrades () {
        return trades;
    }

    @Override
    public String toString() {
        return "RelevantTradesExch:{" +
                "\ndate='" + date + '\'' +
                "\n protocolVersion='" + protocolVersion + '\'' +
                "\n trades=" + "TO BE IMPLEMENTED" + // Arrays.toString(trades) +
                '}';
    }



}
