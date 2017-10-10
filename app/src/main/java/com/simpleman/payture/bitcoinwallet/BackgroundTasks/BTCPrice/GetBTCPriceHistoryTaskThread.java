package com.simpleman.payture.bitcoinwallet.BackgroundTasks.BTCPrice;


import android.support.v4.app.Fragment;
import android.util.Log;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.simpleman.payture.bitcoinwallet.HTTP.AppRequestQueue;
import com.simpleman.payture.bitcoinwallet.HTTP.StatisticRequest;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceChart.BTCPriceHistoryItem;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetBTCPriceHistoryTaskThread {

    private BTCPriceHistoryItem[] btcPriceHistoryItems;
    private IBTCPriceHistoryCallback callback;
    private Thread thread;

    public GetBTCPriceHistoryTaskThread(IBTCPriceHistoryCallback callback){
        this.callback = callback;
        this.thread = new Thread(new Runnable(){
            @Override
            public void run() {
                doInBackground();
            }
        });

    }

    public void doInBackground(){
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
    }

    public void execute(){
        thread.run();
    }

    public void stop() {
        if (thread != null)
            thread.interrupt();
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
