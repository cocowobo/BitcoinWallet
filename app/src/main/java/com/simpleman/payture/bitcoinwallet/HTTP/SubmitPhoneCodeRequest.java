package com.simpleman.payture.bitcoinwallet.HTTP;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Map;


public class SubmitPhoneCodeRequest extends AuthRequest {

    private final static String ROUTE = "api/auth/token";

    public SubmitPhoneCodeRequest(String phone, String code, String token,
                               Response.Listener<JSONObject> responseListener,
                               Response.ErrorListener errorListener) {
        super(ROUTE, setParams(phone, code, token), responseListener, errorListener);
    }

    private static ArrayMap<String, String> setParams(String... param) {
        ArrayMap<String, String> params = new ArrayMap<>();
        params.put("phone", param[0]);
        params.put("code", param[1]);
        params.put("token", param[2]);
        return params;
    }
}
