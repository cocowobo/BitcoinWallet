package com.simpleman.payture.bitcoinwallet.CurrencyExchanger;



public class Exchanger {

    private static Exchanger exchanger;
    private Exchanger(){}

    public static Exchanger getInstance(){
        if (exchanger == null) {
            exchanger = new Exchanger();
        }
        return exchanger;
    }

    private BTCCurrentPrice[] rates = null;

    public synchronized void updateRates(BTCCurrentPrice[] rates) {
        this.rates = rates;
    }

    public Double calculateForward(double btcAmount, String currencyTo) {
        for (BTCCurrentPrice rate : rates) {
            if (rate.getTo().equals(currencyTo))
                return btcAmount * rate.getRate();
        }
        return null;
    }

    public Double calculateReverse(double cur_amount, String currencyFrom) {
        for (BTCCurrentPrice rate : rates) {
            if (rate.getTo().equals(currencyFrom))
                return cur_amount / rate.getRate();
        }
        return null;
    }

    /*
    public static double calculateUSDforBTC(double btc_amount){
        return btc_amount * Currency.USD.getPrice();
    }

    public static double calculateBTCforUSD(double usd_amount){
        return usd_amount / Currency.USD.getPrice();
    }

    public static void updatePrices(){
        Currency.USD.setPrice(4567.89);
    }*/
}
