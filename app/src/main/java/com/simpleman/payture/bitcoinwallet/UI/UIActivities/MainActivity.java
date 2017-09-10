package com.simpleman.payture.bitcoinwallet.UI.UIActivities;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.simpleman.payture.bitcoinwallet.R;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCBuySell.BTCBuyFragment;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceChart.BTCPriceChartFragment;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceInfo.BTCPriceInfoFragment;

public class MainActivity extends AppCompatActivity {

    BTCPriceInfoFragment priceInfoFragment;
    BTCPriceChartFragment priceChartFragment;
    BTCBuyFragment btcBuyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        priceInfoFragment = new BTCPriceInfoFragment();
        priceChartFragment = new BTCPriceChartFragment();
        btcBuyFragment = new BTCBuyFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.btc_price_frame, priceInfoFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_frame, btcBuyFragment).commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        priceInfoFragment = null;
        priceChartFragment = null;
    }
}
