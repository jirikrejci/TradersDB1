package com.JKSoft.DataStructures;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jirka on 25.8.2016.
 */
public class TradeRecord {

    // private memmbers
    @SerializedName("ID")
    Long id;

    @SerializedName("Symbol")
    String symbol;

    @SerializedName("Direction")
    String direction;

    @SerializedName("Status")
    String status;

    @SerializedName("Level Price")
    double levelPrice;

    @SerializedName("Order status")
    String orderStatus;

    @SerializedName("Order #")
    Long orderNumber;

    @SerializedName("Method")
    String method;

    @SerializedName("Req Result")
    String requestResult;

    @SerializedName("Estimated Trade Status")
    String estimatedTradeStatus;




    //constructor


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



    @Override
    public String toString() {
        String output_str ="";
        output_str = "\nTrade> " + symbol +
                "\t " + String.format("%8.4f", levelPrice) +
                "\t" + direction +
                "\t" +  requestResult +
                "\t" + estimatedTradeStatus;

        return output_str;
    }

    public String toStringNice() {
        String output_str ="";
        output_str = "Trade: \n" +
                "\tsymbol: " + symbol + "\n" +
                "\tlevelPrice: " + levelPrice + "\n" +
                "\tdirection: " + direction + "\n";

        return output_str;
    }



}
