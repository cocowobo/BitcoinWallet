package com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCBuySell;


public interface ITransactionEditText {

    void makeAdditionalControlsValid();
    void makeAdditionalControlsInvalid();

    void clearTargetValue();
    void renderTargetValue();
    void saveBasicValue(String text);
}
