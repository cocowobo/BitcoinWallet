package com.simpleman.payture.bitcoinwallet.BackgroundTasks.BTCPrice;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.simpleman.payture.bitcoinwallet.CurrencyExchanger.BTCCurrentPrice;
import com.simpleman.payture.bitcoinwallet.CurrencyExchanger.Exchanger;
import com.simpleman.payture.bitcoinwallet.HTTP.AppRequestQueue;
import com.simpleman.payture.bitcoinwallet.HTTP.GetBTCCurrentPriceRequest;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceChart.BTCPriceHistoryItem;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceInfo.BTCPriceInfoFragment;

import org.json.JSONArray;
import org.json.JSONObject;


public class GetBTCCurrentPriceTaskThread {

    private IBTCPriceCallback callback;
    private Thread thread;
    private final static long TIMEOUT_MILLIS = 10000;
    private boolean isRepeatable = false;

    public GetBTCCurrentPriceTaskThread(IBTCPriceCallback callback, boolean mode) {
        this.callback = callback;
        this.isRepeatable = mode;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                doInBackground();
            }
        });
    }

    public void execute(){ thread.start(); }
    public void stop(){ thread.interrupt(); }

    private void doInBackground() {

        GetBTCCurrentPriceRequest request = new GetBTCCurrentPriceRequest(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("GetBTCCurrentPriceTask", response.toString());
                BTCCurrentPrice[] prices = parseBTCCurrentPriceJSON(response);
                Exchanger.getInstance().updateRates(prices);
                callback.onGetBTCPriceInfo(prices);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("GetBTCCurrentPriceTask", error.toString());
            }
        });

        if (isRepeatable)
        {
            while (true) {
                AppRequestQueue.getInstance(((Fragment)callback).getContext()).addToRequestQueue(request);
                waitTimeout();
            }
        }
        else
        {
            AppRequestQueue.getInstance(((Fragment)callback).getContext()).addToRequestQueue(request);
        }
    }

    private void waitTimeout() {
        try {
            Thread.sleep(TIMEOUT_MILLIS);
        } catch (Exception ex) {
            Log.e("GetBTCCurrentPriceTask", ex.toString());
        }
    }


    private BTCCurrentPrice[] parseBTCCurrentPriceJSON(JSONObject BTCCurrentPriceJSON) {
        // на будущее
        /*if (BTCCurrentPriceJSON != null) {
            Gson gson = new Gson();
            JSONArray jsonArray = BTCCurrentPriceJSON.optJSONArray("rates");

            if (jsonArray != null) {
                BTCCurrentPrice[] currentPrices = gson.fromJson(jsonArray.toString(), BTCCurrentPrice[].class);
                return currentPrices;
            }
        }
        return null;*/
        if (BTCCurrentPriceJSON != null) {
            Gson gson = new Gson();
            BTCCurrentPrice currentPrice = gson.fromJson(BTCCurrentPriceJSON.toString(), BTCCurrentPrice.class);

            BTCCurrentPrice[] prices = new BTCCurrentPrice[1];
            prices[0] = currentPrice;

            return prices;
        }
        return null;
    }

}
