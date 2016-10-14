package com.JKSoft.TdbClient.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Structure for receiving data from TDB server
 * Created by Jirka on 28.8.2016.
 */
public class RelevantTradesExch {
    @SerializedName("date created")
    private String date;

    @SerializedName("protocol version")
    private String protocolVersion;

    private ArrayList<TradeRecord> trades;

    /**
     * Returns list of trades from RelevantReadesExch object
     * @return
     */
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
