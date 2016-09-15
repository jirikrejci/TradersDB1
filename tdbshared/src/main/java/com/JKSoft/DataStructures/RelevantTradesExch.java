package com.JKSoft.DataStructures;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by Jirka on 28.8.2016.
 */
public class RelevantTradesExch {
    @SerializedName("date created")
    String date;

    @SerializedName("protocol version")
    String protocolVersion;

    TradeRecord [] trades;

    @Override
    public String toString() {
        return "RelevantTradesExch:{" +
                "\ndate='" + date + '\'' +
                "\n protocolVersion='" + protocolVersion + '\'' +
                "\n trades=" + Arrays.toString(trades) +
                '}';
    }
}
