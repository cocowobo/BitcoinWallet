package com.simpleman.payture.bitcoinwallet.CurrencyExchanger;


public enum Currency {
    USD(1), BTC(1);

    Currency(double price){
        setPrice(1);
    }

    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
