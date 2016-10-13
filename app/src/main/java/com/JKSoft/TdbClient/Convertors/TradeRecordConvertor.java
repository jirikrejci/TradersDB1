package com.JKSoft.TdbClient.convertors;

/**
 * Created by Jirka on 5.10.2016.
 */
public class TradeRecordConvertor {
    public static String tradeStatus2Text(String estimatedTradeStatus) {
        switch (estimatedTradeStatus) {
            case "TS_PENDING": return "PENDING";
            case "TS_EARLY_TURN": return "EARLY TURN";
            case "TS_IN": return "IN";
            case "TS_WAITING_FOR_SCRATCH": return "SCRATCH MODE";
            case "TS_IN_TEST": return "IN TEST";
            case "TS_TP_REACHED": return ("TARGET");
            case "TS_STOP_LOSS_REACHED": return ("STOP LOSS");
            case "TS_SCRATCH_REACHED": return ("SCRATCH");
            default: return estimatedTradeStatus;
        }
    }
}
