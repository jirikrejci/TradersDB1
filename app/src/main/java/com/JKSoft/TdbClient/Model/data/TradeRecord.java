package com.JKSoft.TdbClient.model.data;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Definition of Trade record class
  * Created by Jirka on 25.8.2016.
 */
public class TradeRecord extends RealmObject {

   // private memmbers
    @PrimaryKey
    @SerializedName("ID")
    private Long tradeId;

    @SerializedName("Symbol")
    private String symbol;

    @SerializedName("Direction")
    private String direction;

    @SerializedName("Status")
    private String tradeRecordStatus;

    @SerializedName("Level Price")
    private Double levelPrice;


    @SerializedName("Order status")
    private String orderStatus;

    @SerializedName("Order #")
    private Long orderNumber;

    @SerializedName("Method")
    private String method;

    @SerializedName("Req Result")
    private String requestResult;

    @SerializedName("Stop Loss")
    private Double SL;

    @SerializedName("SL Pips")
    private Double slPips;

    @SerializedName("Manual target")
    private Double tpManual;

    @SerializedName("Proposed Target")
    private Double tpProposed;

    @SerializedName("Planned RRR")
    private Double rrrPlanned;

    @SerializedName("SL EUR")
    private Double slInMoney;

    @SerializedName("Estimated Trade Status")
    private String estimatedTradeStatus;

    @SerializedName("Level Distance")
    private Double levelDistance;


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


    //constructors

    /**
     * Constructor generating empty object with default values
     */
    public TradeRecord() {
    }

    /**
     * Contructor with them most important elements
     * @param symbol
     * @param levelPrice
     * @param direction
     */
    public TradeRecord(String symbol, Double levelPrice, String direction) {
        this.symbol = symbol;
        this.levelPrice = levelPrice;
        this.direction = direction;
    }


    // Setters and Getters

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

    /**
     * Returns easy readable presentation of TradeRecord object
     * @return
     */

    public String toStringNice() {
        String output_str = "";
        output_str = "Trade: \n" +
                "\tsymbol: " + symbol + "\n" +
                "\tlevelPrice: " + levelPrice + "\n" +
                "\tdirection: " + direction + "\n";
        return output_str;
    }


}
