package com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceChart;


public class BTCPriceHistoryItem {

    private long x;
    private float y;

    public BTCPriceHistoryItem(long x, float y) {
        this.x = x;
        this.y = y;
    }

    public long getDate() {
        return this.x;
    }

    public float getPrice() {
        return this.y;
    }
}
