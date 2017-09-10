package com.simpleman.payture.bitcoinwallet.HTTP;

import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class StatisticRequest extends Request<JSONObject> {

    private Map<String, String> params;
    private Response.Listener<JSONObject> listener;
    private final static String URL = "https://api.blockchain.info/charts/market-price";

    public StatisticRequest(Map<String,String> params,
                            Response.Listener<JSONObject> responseListener,
                            Response.ErrorListener errorListener) {
        super(Method.GET, URL, errorListener);
        this.listener = responseListener;
        this.params = params;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            Log.e("StatisticRequest", e.toString());
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            Log.e("StatisticRequest", je.toString());
            return Response.error(new ParseError(je));
        }
    }

}
