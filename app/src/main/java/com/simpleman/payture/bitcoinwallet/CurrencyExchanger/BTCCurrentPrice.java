package com.simpleman.payture.bitcoinwallet.CurrencyExchanger;


public class BTCCurrentPrice {

    private String from;
    private String to;
    private double rate;
    private long timestamp;


    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public double getRate() {
        return rate;
    }

    public long getTimestamp() {
        return timestamp;
    }


    public BTCCurrentPrice(String from, String to, double rate, long timestamp) {
        this.from = from;
        this.to = to;
        this.rate = rate;
        this.timestamp = timestamp;
    }

}
