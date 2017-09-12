package com.simpleman.payture.bitcoinwallet.UI.UIActivities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.simpleman.payture.bitcoinwallet.R;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCBuySell.BTCPurchaseFragment;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCBuySell.BTCSaleFragment;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceChart.BTCPriceChartFragment;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceInfo.BTCPriceInfoFragment;
import com.simpleman.payture.bitcoinwallet.Utils.MainState;
import com.simpleman.payture.bitcoinwallet.Utils.Tags;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private Fragment mainFragment;
    private Fragment infoFragment;

    private String state = MainState.DASHBOARD;
    private boolean deviceRotated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState != null && savedInstanceState.getString(Tags.DEVICE_ROTATION_EVENT) != null) {
            fragmentManager = getSupportFragmentManager();
            mainFragment = fragmentManager.getFragment(savedInstanceState, Tags.MAIN_FRAGMENT);
            infoFragment = fragmentManager.getFragment(savedInstanceState, Tags.INFO_FRAGMENT);
            state = savedInstanceState.getString(Tags.MAIN_STATE);
        } else {
            fragmentManager = getSupportFragmentManager();
            mainFragment = new BTCPriceChartFragment();
            infoFragment = new BTCPriceInfoFragment();
            state = MainState.DASHBOARD;
        }

        fragmentManager.beginTransaction().replace(R.id.main_frame, mainFragment).commit();
        fragmentManager.beginTransaction().replace(R.id.btc_price_frame, infoFragment).commit();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        deviceRotated = true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (true) { //???
            outState.putString(Tags.DEVICE_ROTATION_EVENT, Tags.DEVICE_ROTATION_EVENT);
            fragmentManager.putFragment(outState, Tags.MAIN_FRAGMENT, mainFragment);
            fragmentManager.putFragment(outState, Tags.INFO_FRAGMENT, infoFragment);
            outState.putString(Tags.MAIN_STATE, state);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // TODO: 12.09.2017 продумать и добавить опции меню 
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
            //return true;
        //}

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_dashboard:
                {
                    if (!state.equals(MainState.DASHBOARD)) {
                        mainFragment = new BTCPriceChartFragment();
                        fragmentManager.beginTransaction().replace(R.id.main_frame, mainFragment).commit();
                    }
                    state = MainState.DASHBOARD;
                    break;
                }
            case R.id.nav_btc_purchase:
                {
                    if (!state.equals(MainState.PURCHASE)) {
                        mainFragment = BTCPurchaseFragment.newInstance();
                        fragmentManager.beginTransaction().replace(R.id.main_frame, mainFragment).commit();
                    }
                    state = MainState.PURCHASE;
                    break;
                }
            case R.id.nav_btc_sale:
            {
                if (!state.equals(MainState.SALE)) {
                    mainFragment = BTCSaleFragment.newInstance();
                    fragmentManager.beginTransaction().replace(R.id.main_frame, mainFragment).commit();
                }
                state = MainState.SALE;
                break;
            }
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
