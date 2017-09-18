package com.simpleman.payture.bitcoinwallet.HTTP;

import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class StatisticRequest extends JsonObjectRequest {

    private final static String URL = "https://api.blockchain.info/charts/market-price";
    private final static int METHOD = Method.GET;

    public StatisticRequest(Response.Listener<JSONObject> responseListener,
                            Response.ErrorListener errorListener) {
        super(METHOD, URL, null, responseListener, errorListener);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        HashMap<String, String> params = new HashMap<>();
        params.put("format", "json");
        return params;
    }
}
