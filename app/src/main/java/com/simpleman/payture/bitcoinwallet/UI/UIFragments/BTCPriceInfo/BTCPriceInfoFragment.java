package com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceInfo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.simpleman.payture.bitcoinwallet.BackgroundTasks.BTCPrice.GetBTCCurrentPriceTaskThread;
import com.simpleman.payture.bitcoinwallet.BackgroundTasks.BTCPrice.IBTCPriceCallback;
import com.simpleman.payture.bitcoinwallet.CurrencyExchanger.BTCCurrentPrice;
import com.simpleman.payture.bitcoinwallet.R;


public class BTCPriceInfoFragment extends Fragment implements IBTCPriceCallback {

    private IBTCPriceCallback callback;
    private GetBTCCurrentPriceTaskThread task;

    private TextView field;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_btc_price_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callback = this;
        field = (TextView)view.findViewById(R.id.currency_BTC_USD_price);
        startUpdateBTCRatesTask();
    }


    private void startUpdateBTCRatesTask() {
        task = new GetBTCCurrentPriceTaskThread(callback, true);
        task.execute();
    }


    @Override
    public void onGetBTCPriceInfo(BTCCurrentPrice[] prices) {

        for (BTCCurrentPrice price : prices) {

            //TextView field = (TextView)getView().findViewById(R.id.currency_BTC_USD_price);

            if (field != null)
                field.setText(String.valueOf(price.getRate()));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        callback = null;
        task.stop();
    }

}
