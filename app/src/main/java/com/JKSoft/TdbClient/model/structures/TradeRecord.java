package com.JKSoft.TdbClient.model.structures;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Jirka on 25.8.2016.
 */
public class TradeRecord extends RealmObject {

   // private memmbers
    @PrimaryKey
    @SerializedName("ID")
    Long tradeId;

    @SerializedName("Symbol")
    String symbol;

    @SerializedName("Direction")
    String direction;

    @SerializedName("Status")
    String tradeRecordStatus;

    @SerializedName("Level Price")
    Double levelPrice;


    @SerializedName("Order status")
    String orderStatus;

    @SerializedName("Order #")
    Long orderNumber;

    @SerializedName("Method")
    String method;

    @SerializedName("Req Result")
    String requestResult;

    @SerializedName("Stop Loss")
    Double SL;

    @SerializedName("SL Pips")
    Double slPips;

    @SerializedName("Manual target")
    Double tpManual;

    @SerializedName("Proposed Target")
    Double tpProposed;

    @SerializedName("Planned RRR")
    Double rrrPlanned;

    @SerializedName("SL EUR")
    Double slInMoney;





    @SerializedName("Estimated Trade Status")
    String estimatedTradeStatus;

    @SerializedName("Level Distance")
    Double levelDistance;





    // Setters and Getters

    public Double getLevelDistance() {return levelDistance; }
    public void setLevelDistance(Double levelDistance) {
        this.levelDistance = levelDistance;
    }


    public Double getSL() {
        return SL;
    }

    public void setSL(Double SL) {
        this.SL = SL;
    }

    public Double getSlPips() {
        return slPips;
    }

    public void setSlPips(Double slPips) {
        this.slPips = slPips;
    }

    public Double getTpManual() {
        return tpManual;
    }

    public void setTpManual(Double tpManual) {
        this.tpManual = tpManual;
    }

    public Double getTpProposed() {
        return tpProposed;
    }

    public void setTpProposed(Double tpProposed) {
        this.tpProposed = tpProposed;
    }

    public Double getRrrPlanned() {
        return rrrPlanned;
    }

    public void setRrrPlanned(Double rrrPlanned) {
        this.rrrPlanned = rrrPlanned;
    }

    public Double getSlInMoney() {
        return slInMoney;
    }

    public void setSlInMoney(Double slInMoney) {
        this.slInMoney = slInMoney;
    }

    public String getEstimatedTradeStatus() {
        return estimatedTradeStatus;
    }

    public void setEstimatedTradeStatus(String estimatedTradeStatus) {
        this.estimatedTradeStatus = estimatedTradeStatus;
    }

    public String getRequestResult() {
        return requestResult;
    }

    public void setRequestResult(String requestResult) {
        this.requestResult = requestResult;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }





    //constructors
    public TradeRecord() {

    }

    public TradeRecord(String symbol, Double levelPrice, String direction) {
        this.symbol = symbol;
        this.levelPrice = levelPrice;
        this.direction = direction;
    }


    // Setters and Getters

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getLevelPrice() {
        return levelPrice;
    }

    public void setLevelPrice(Double levelPrice) {
        this.levelPrice = levelPrice;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Long getTradeId() {return tradeId; }

    public void setTradeId(Long tradeId) {this.tradeId = tradeId; }

    public String getTradeRecordStatus() {return tradeRecordStatus;}   //TODO dořešit

    public void setTradeRecordStatus(String tradeRecordStatus) {this.tradeRecordStatus = tradeRecordStatus; } // TODO Dořešit

    public String getMethod() {return method;}

    public void setMethod(String method) {this.method = method;}

    @Override
    public String toString() {
        String output_str = "";
        output_str = "\nTrade> " +
                "id_" + tradeId +
                "\t" +symbol +
                "\t " + String.format("%8.4f", levelPrice) +
                "\t" + direction +
                "\t" + requestResult +
                "\t" + estimatedTradeStatus;

        return output_str;
    }

    public String toStringNice() {
        String output_str = "";
        output_str = "Trade: \n" +
                "\tsymbol: " + symbol + "\n" +
                "\tlevelPrice: " + levelPrice + "\n" +
                "\tdirection: " + direction + "\n";

        return output_str;
    }


}
