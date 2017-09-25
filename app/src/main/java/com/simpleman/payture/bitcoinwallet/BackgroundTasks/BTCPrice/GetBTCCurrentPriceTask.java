package com.simpleman.payture.bitcoinwallet.BackgroundTasks.BTCPrice;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import com.simpleman.payture.bitcoinwallet.CurrencyExchanger.Exchanger;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceInfo.BTCPriceInfoFragment;


public class GetBTCCurrentPriceTask extends AsyncTask<Void, Void, Void> {

    private IBTCPriceCallback callback;

    public GetBTCCurrentPriceTask(IBTCPriceCallback callback){
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Thread.sleep(3000);
            Exchanger.updatePrices();
        } catch (Exception ex) {}
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        callback.onGetBTCPriceInfo();
    }

}
