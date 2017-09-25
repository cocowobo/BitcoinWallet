package com.simpleman.payture.bitcoinwallet.BackgroundTasks.BTCPrice;

import android.content.Context;
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


public class GetBTCPriceHistoryTask extends AsyncTask<Void,Void,Void> {

    private BTCPriceHistoryItem[] btcPriceHistoryItems;
    private IBTCPriceHistoryCallback callback;

    public GetBTCPriceHistoryTask(IBTCPriceHistoryCallback callback) {
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Void... params) {

        StatisticRequest request = new StatisticRequest(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("GetBTCPriceHistoryTask", response.toString());
                btcPriceHistoryItems = parseBTCPriceHistoryJSON(response);
                callback.onGetBTCPriceHistory(btcPriceHistoryItems);
                return;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("GetBTCPriceHistoryTask", error.toString());
                btcPriceHistoryItems = null;
                callback.onGetBTCPriceHistory(btcPriceHistoryItems);
                return;
            }
        });

        AppRequestQueue.getInstance(((Fragment)callback).getContext()).addToRequestQueue(request);
        return null;
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

