package com.simpleman.payture.bitcoinwallet.BackgroundTasks;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.simpleman.payture.bitcoinwallet.HTTP.AppRequestQueue;
import com.simpleman.payture.bitcoinwallet.HTTP.StatisticRequest;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceChart.BTCChart;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceChart.BTCPriceHistoryItem;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class GetBTCPriceHistoryTask extends AsyncTask<Fragment,Void,Void> {

    private BTCPriceHistoryItem[] btcPriceHistoryItems;
    private Fragment fragment;
    private BTCChart btcChart;

    @Override
    protected Void doInBackground(Fragment... fragments) {
        this.fragment = fragments[0];

        StatisticRequest request = new StatisticRequest(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("GetBTCPriceHistoryTask", response.toString());
                btcPriceHistoryItems = parseBTCPriceHistoryJSON(response);
                drawChart();
                return;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("GetBTCPriceHistoryTask", error.toString());
                btcPriceHistoryItems = null;
                drawChart();
                return;
            }
        });

        AppRequestQueue.getInstance(this.fragment.getContext()).addToRequestQueue(request);
        return null;
    }

    private void drawChart() {
        if (fragment.getView() != null)
            btcChart = new BTCChart(btcPriceHistoryItems, fragment);
    }

    private BTCPriceHistoryItem[] parseBTCPriceHistoryJSON(JSONObject BTCPriceHistoryJSON){
        if (BTCPriceHistoryJSON != null) {
            Gson gson = new Gson();
            JSONArray jsonArray = BTCPriceHistoryJSON.optJSONArray("values");

            if (jsonArray != null) {
                BTCPriceHistoryItem[] priceHistoryItems = gson.fromJson(jsonArray.toString(), BTCPriceHistoryItem[].class);
                return priceHistoryItems;
            }
        }
        return null;
    }

}

