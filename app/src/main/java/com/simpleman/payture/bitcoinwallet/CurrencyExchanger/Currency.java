package com.simpleman.payture.bitcoinwallet.CurrencyExchanger;


public enum Currency {
    USD, EUR, BTC;

    public static Currency getByName(String name) {
        for ( Currency mode : Currency.values() )
            if ( mode.name() == name ) {
                return mode;
            }
        return USD;
    }
}
