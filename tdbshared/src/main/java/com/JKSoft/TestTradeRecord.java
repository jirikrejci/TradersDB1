package com.JKSoft;

import com.JKSoft.DataExchange.TradeRecordsReader;
import com.JKSoft.DataStructures.RelevantTradesExch;
import com.JKSoft.Networking.fms.Ftp;
import com.JKSoft.Networking.gson.JsonConversions;

/**
 * Created by Jirka on 18.9.2016.
 */
public class TestTradeRecord {
    public static void main (String [] args) {
         RelevantTradesExch relevantTradesExch= TradeRecordsReader.readTradeRecors();
         String jsonPretty = JsonConversions.getJsonPretty(relevantTradesExch);
        Ftp.writeStringToFtp("/FilesDB/RelevantTradesPretty.json", jsonPretty);



    }



}
