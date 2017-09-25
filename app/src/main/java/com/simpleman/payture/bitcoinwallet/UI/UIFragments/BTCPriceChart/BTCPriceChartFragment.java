package com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceChart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simpleman.payture.bitcoinwallet.BackgroundTasks.BTCPrice.IBTCPriceHistoryCallback;
import com.simpleman.payture.bitcoinwallet.R;
import com.simpleman.payture.bitcoinwallet.BackgroundTasks.BTCPrice.GetBTCPriceHistoryTask;


public class BTCPriceChartFragment extends Fragment implements IBTCPriceHistoryCallback {

    private IBTCPriceHistoryCallback callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_btc_price_chart, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callback = this;

        GetBTCPriceHistoryTask task = new GetBTCPriceHistoryTask(callback);
        task.execute();
    }

    @Override
    public void onGetBTCPriceHistory(BTCPriceHistoryItem[] btcPriceHistoryItems) {
        if (this.getView() != null)
            new BTCChart(btcPriceHistoryItems, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        callback = null;
    }
}
