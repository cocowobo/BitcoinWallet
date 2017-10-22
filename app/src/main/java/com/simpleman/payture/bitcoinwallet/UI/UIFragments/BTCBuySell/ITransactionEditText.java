package com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCBuySell;


public interface ITransactionEditText {

    void makeControlsValid();
    void makeControlsInvalid();
    boolean isTransactionCostValid();

    void resetTargetValue();
    void renderTargetValue();
    void setTargetValue(String text);
}
