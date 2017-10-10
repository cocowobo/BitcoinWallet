package com.simpleman.payture.bitcoinwallet.HTTP;

import android.support.v4.util.ArrayMap;
import com.android.volley.Response;
import org.json.JSONObject;

public class GetPhoneCodeRequest extends PilxRequest {

    private static final String ROUTE = "api/auth/code";


    public GetPhoneCodeRequest(String phone, String token,
                               Response.Listener<JSONObject> responseListener,
                               Response.ErrorListener errorListener) {
        super(ROUTE, setParams(phone, token), responseListener, errorListener);
    }

    private static ArrayMap<String, String> setParams(String... param) {
        ArrayMap<String, String> params = new ArrayMap<>();
        params.put("phone", param[0]);
        params.put("token", param[1]);
        return params;
    }
}
