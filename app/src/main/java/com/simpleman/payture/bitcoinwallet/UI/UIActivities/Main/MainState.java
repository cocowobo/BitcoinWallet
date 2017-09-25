package com.simpleman.payture.bitcoinwallet.UI.UIActivities.Main;

enum MainState {
    DASHBOARD, PURCHASE, SALE;

    public static MainState getByCode(int code){
        for (MainState state : MainState.values()) {
            if (code == state.ordinal())
                return state;
        }
        return null;
    }
}
