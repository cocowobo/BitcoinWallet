package com.simpleman.payture.bitcoinwallet.StatisticEngine.BackgroundTasks;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceInfo.BTCPriceInfoFragment;


public class GetBTCCurrentPriceTask extends AsyncTask<Void, Void, Void> {

    private Fragment caller_fragment;

    public GetBTCCurrentPriceTask(Fragment fragment){
        caller_fragment = fragment;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Thread.sleep(3000);
        } catch (Exception ex) {}
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        ((BTCPriceInfoFragment)caller_fragment).stopAnimation();
        ((BTCPriceInfoFragment)caller_fragment).fillFields();
    }

}
