package com.simpleman.payture.bitcoinwallet.BackgroundTasks.BTCPrice;

import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceChart.BTCPriceHistoryItem;

public interface IBTCPriceHistoryCallback {
    void onGetBTCPriceHistory(BTCPriceHistoryItem[] btcPriceHistoryItems);
}
