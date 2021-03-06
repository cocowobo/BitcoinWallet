package com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceChart;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simpleman.payture.bitcoinwallet.Application.Application;
import com.simpleman.payture.bitcoinwallet.BackgroundTasks.BTCPrice.GetBTCPriceHistoryTaskThread;
import com.simpleman.payture.bitcoinwallet.BackgroundTasks.BTCPrice.IBTCPriceHistoryCallback;
import com.simpleman.payture.bitcoinwallet.R;
import com.simpleman.payture.bitcoinwallet.BackgroundTasks.BTCPrice.GetBTCPriceHistoryTask;


public class BTCPriceChartFragment extends Fragment implements IBTCPriceHistoryCallback {

    private IBTCPriceHistoryCallback callback;
    GetBTCPriceHistoryTaskThread task;

    // TODO: 10/24/2017 Добавить кэширование данных истории 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_btc_price_chart, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Application.getInstance().checkBitcoinWalletAddress( getFragmentManager() );
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callback = this;

        task = new GetBTCPriceHistoryTaskThread(callback);
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
        task.stop();
    }
}
