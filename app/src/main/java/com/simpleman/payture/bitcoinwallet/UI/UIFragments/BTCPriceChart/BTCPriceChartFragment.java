package com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceChart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simpleman.payture.bitcoinwallet.R;
import com.simpleman.payture.bitcoinwallet.BackgroundTasks.GetBTCPriceHistoryTask;


public class BTCPriceChartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_btc_price_chart, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GetBTCPriceHistoryTask task = new GetBTCPriceHistoryTask();
        task.execute(this);

    }
}
