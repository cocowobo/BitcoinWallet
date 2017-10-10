package com.simpleman.payture.bitcoinwallet.HTTP;

import com.android.volley.Response;

import org.json.JSONObject;

public class GetBTCCurrentPriceRequest extends PilxRequest {

    private static final String ROUTE = "api/rate";

    public GetBTCCurrentPriceRequest(Response.Listener<JSONObject> responseListener,
                                     Response.ErrorListener errorListener) {
        super(Method.GET, ROUTE, null, responseListener, errorListener);
    }
}
