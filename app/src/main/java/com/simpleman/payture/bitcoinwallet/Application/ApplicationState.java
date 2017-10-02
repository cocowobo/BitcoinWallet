package com.simpleman.payture.bitcoinwallet.Application;

public enum ApplicationState {
    DASHBOARD, PURCHASE, SALE;

    public static ApplicationState getByCode(int code){
        for (ApplicationState state : ApplicationState.values()) {
            if (code == state.ordinal())
                return state;
        }
        return null;
    }
}
