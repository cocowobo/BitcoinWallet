package com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceChart;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.LineChart;
import com.google.gson.Gson;
import com.simpleman.payture.bitcoinwallet.HTTP.AppRequestQueue;
import com.simpleman.payture.bitcoinwallet.HTTP.StatisticRequest;
import com.simpleman.payture.bitcoinwallet.R;
import com.simpleman.payture.bitcoinwallet.StatisticEngine.BackgroundTasks.GetBTCPriceHistoryTask;
import com.simpleman.payture.bitcoinwallet.Utils.Tags;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


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
