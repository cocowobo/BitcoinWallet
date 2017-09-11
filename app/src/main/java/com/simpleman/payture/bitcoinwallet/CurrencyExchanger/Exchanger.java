package com.simpleman.payture.bitcoinwallet.CurrencyExchanger;



public class Exchanger {

    public static double calculateUSDforBTC(double btc_amount){
        return btc_amount * Currency.USD.getPrice();
    }

    public static double calculateBTCforUSD(double usd_amount){
        return usd_amount / Currency.USD.getPrice();
    }

    public static void updatePrices(){
        Currency.USD.setPrice(4567.89);
    }
}
