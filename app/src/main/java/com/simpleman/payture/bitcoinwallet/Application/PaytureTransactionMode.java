package com.simpleman.payture.bitcoinwallet.Application;

public enum PaytureTransactionMode {
    PURCHASE, SALE;

    public static PaytureTransactionMode getByName(String name) {
        for ( PaytureTransactionMode mode : PaytureTransactionMode.values() )
            if ( mode.name() == name ) {
                return mode;
            }
        return PURCHASE;
    }
}
