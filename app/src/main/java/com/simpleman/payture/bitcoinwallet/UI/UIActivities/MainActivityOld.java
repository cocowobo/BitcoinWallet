package com.simpleman.payture.bitcoinwallet.UI.UIActivities;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.simpleman.payture.bitcoinwallet.R;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCBuySell.BTCSaleFragment;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceInfo.BTCPriceInfoFragment;

public class MainActivityOld extends AppCompatActivity {

    BTCPriceInfoFragment priceInfoFragment;
    Fragment mainFragment;

    private boolean rotated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old);

        if (savedInstanceState == null) {
            priceInfoFragment = new BTCPriceInfoFragment();
            mainFragment = BTCSaleFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.btc_price_frame, priceInfoFragment).commit();
            fragmentManager.beginTransaction().replace(R.id.main_frame, mainFragment).commit();
        } else {
            mainFragment = getSupportFragmentManager().getFragment(savedInstanceState, "MAIN_FRAGMENT");
            priceInfoFragment = new BTCPriceInfoFragment();
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
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "MAIN_FRAGMENT", mainFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
