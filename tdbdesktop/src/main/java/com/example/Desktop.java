package com.example;

import com.JKSoft.DataExchange.TradeRecordsReader;
import com.JKSoft.DataStructures.RelevantTradesExch;
import com.JKSoft.Networking.gson.JsonConversions;
import com.JKSoft.Networking.tests.HealthCheck;

public class Desktop {
    public static void main (String [] args) {
        System.out.println("Hlasi se desktop");
        HealthCheck.sayHello();

        RelevantTradesExch relTradesExch = TradeRecordsReader.readTradeRecors();
        System.out.println(JsonConversions.getJsonPretty(relTradesExch));

    }
}
