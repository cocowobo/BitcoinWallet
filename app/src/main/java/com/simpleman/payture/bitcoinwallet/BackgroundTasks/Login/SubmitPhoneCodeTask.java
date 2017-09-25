package com.simpleman.payture.bitcoinwallet.BackgroundTasks.Login;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.simpleman.payture.bitcoinwallet.HTTP.AppRequestQueue;
import com.simpleman.payture.bitcoinwallet.HTTP.SubmitPhoneCodeRequest;

import org.json.JSONObject;


public class SubmitPhoneCodeTask extends AsyncTask<String, Void, Void> {

    private Context context;
    private IAsyncAuthCallback callback;

    public SubmitPhoneCodeTask(Context context, IAsyncAuthCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(String... params) {
        String phone = params[0] == null ? "" : params[0];
        String code = params[1] == null ? "" : params[1];
        String token = params[2] == null ? "" : params[2];

        SubmitPhoneCodeRequest request = new SubmitPhoneCodeRequest(phone, code, token,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("SubmitPhoneCodeTask", response.toString());
                        callback.onSubmitCodeResult(new AuthResponse(true));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("SubmitPhoneCodeTask", error.toString());
                        callback.onSubmitCodeResult(new AuthResponse(false));
                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Log.i("SubmitCodeTask", "Starting request... " + request.toString());
        AppRequestQueue.getInstance(context).addToRequestQueue(request);

        return null;
    }
}
