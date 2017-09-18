package com.simpleman.payture.bitcoinwallet.BackgroundTasks.Login;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.simpleman.payture.bitcoinwallet.HTTP.AppRequestQueue;
import com.simpleman.payture.bitcoinwallet.HTTP.GetPhoneCodeRequest;
import org.json.JSONObject;


public class GetPhoneCodeTask extends AsyncTask<String, Void, Void> {

    private Context context;
    IAsyncAuthCallback asyncGetCodeResult;

    public GetPhoneCodeTask(Context context, IAsyncAuthCallback result){
        this.context = context;
        this.asyncGetCodeResult = result;
    }

    @Override
    protected Void doInBackground(String... params) {
        String phone = params[0] == null ? "" : params[0];
        String token = params[1] == null ? "" : params[1];

        GetPhoneCodeRequest request = new GetPhoneCodeRequest(phone, token,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Response", response.toString());
                        asyncGetCodeResult.onGetCodeResult(new AuthResponse(true));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                        asyncGetCodeResult.onGetCodeResult(new AuthResponse(false));
                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppRequestQueue.getInstance(context).addToRequestQueue(request);
        return null;
    }

}
