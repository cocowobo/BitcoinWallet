package com.simpleman.payture.bitcoinwallet.BackgroundTasks.BTCPrice;

import com.simpleman.payture.bitcoinwallet.CurrencyExchanger.BTCCurrentPrice;

public interface IBTCPriceCallback {
    void onGetBTCPriceInfo(BTCCurrentPrice[] btcCurrentPrices);
}
