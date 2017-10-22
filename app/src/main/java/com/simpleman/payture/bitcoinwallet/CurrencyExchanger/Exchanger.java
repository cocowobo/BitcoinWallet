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

    private BTCCurrentPrice[] rates = { new BTCCurrentPrice("BTC", "USD" , 4123.45, 123),
                                        new BTCCurrentPrice("BTC", "EUR" , 3000.01, 123), };

    public synchronized void updateRates(BTCCurrentPrice[] rates) {
        this.rates = rates;
    }

    public double calculateForward(double btcAmount, String currencyTo) {
        if (rates != null) {
            for (BTCCurrentPrice rate : rates) {
                if (rate.getTo().equals(currencyTo))
                    return btcAmount * rate.getRate();
            }
        }
        return 0;
    }

    public double calculateReverse(double cur_amount, String currencyFrom) {
        if (rates != null) {
            for (BTCCurrentPrice rate : rates) {
                if (rate.getTo().equals(currencyFrom))
                    return cur_amount / rate.getRate();
            }
        }
        return 0;
    }
}
