package com.simpleman.payture.bitcoinwallet.UI.UIActivities;

import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.simpleman.payture.bitcoinwallet.R;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCBuySell.BTCPurchaseFragment;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCBuySell.BTCSaleFragment;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceChart.BTCPriceChartFragment;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceInfo.BTCPriceInfoFragment;
import com.simpleman.payture.bitcoinwallet.Utils.Tags;

public class MainActivity extends AppCompatActivity {

    BTCPriceInfoFragment priceInfoFragment;
    Fragment mainFragment;

    private boolean rotated = false;

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (outState != null) {
            rotated = outState.getBoolean(Tags.DEVICE_ROTATION_EVENT);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!rotated) {
            priceInfoFragment = new BTCPriceInfoFragment();
            mainFragment = BTCSaleFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.btc_price_frame, priceInfoFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.main_frame, mainFragment).commit();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        rotated = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        getSupportFragmentManager().putFragment(outState, "");
        outState.putBoolean(Tags.DEVICE_ROTATION_EVENT, rotated);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
