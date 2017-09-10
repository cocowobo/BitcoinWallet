package com.simpleman.payture.bitcoinwallet.HTTP;


import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public  class AppRequestQueue {
    private static AppRequestQueue queueInstance;
    private static RequestQueue mRequestQueue;
    private static Context context;

    private AppRequestQueue(Context ctx){
        context = ctx;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized AppRequestQueue getInstance(Context context){
        if (queueInstance == null) {
            queueInstance = new AppRequestQueue(context);
        }
        return queueInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
