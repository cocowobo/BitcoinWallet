package com.simpleman.payture.bitcoinwallet.UI.UIActivities.Main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.simpleman.payture.bitcoinwallet.Application.Application;
import com.simpleman.payture.bitcoinwallet.Application.ApplicationState;
import com.simpleman.payture.bitcoinwallet.R;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCBuySell.BTCPurchaseFragment;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCBuySell.BTCSaleFragment;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceChart.BTCPriceChartFragment;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceInfo.BTCPriceInfoFragment;
import com.simpleman.payture.bitcoinwallet.Utils.Tags;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private Fragment mainFragment;
    private Fragment infoFragment;
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

        if (savedInstanceState != null ) {
            if (savedInstanceState.getString(Tags.DEVICE_ROTATION_EVENT) != null) {
                fragmentManager = getSupportFragmentManager();
                mainFragment = fragmentManager.getFragment(savedInstanceState, Tags.MAIN_FRAGMENT);
                infoFragment = fragmentManager.getFragment(savedInstanceState, Tags.INFO_FRAGMENT);
                Application.getInstance(this).setState(ApplicationState.getByCode(savedInstanceState.getInt(Tags.MAIN_STATE)));
            }

            if (savedInstanceState.getString(Tags.PHONE) != null) {
                Application.getInstance(this).setUserPhone(savedInstanceState.getString(Tags.PHONE));
            }

            } else {
            fragmentManager = getSupportFragmentManager();
            mainFragment = new BTCPriceChartFragment();
            infoFragment = new BTCPriceInfoFragment();
            Application.getInstance(this).setState(ApplicationState.DASHBOARD);
        }

        fragmentManager.beginTransaction().replace(R.id.main_frame, mainFragment).commit();
        fragmentManager.beginTransaction().replace(R.id.btc_price_frame, infoFragment).commit();

        initNavView();
        initOnTouchListener();
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
            outState.putInt(Tags.MAIN_STATE, Application.getInstance().getState().ordinal());
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
                    if (!Application.getInstance().getState().equals(ApplicationState.DASHBOARD)) {
                        mainFragment = new BTCPriceChartFragment();
                        fragmentManager.beginTransaction().replace(R.id.main_frame, mainFragment).commit();
                    }
                    Application.getInstance().setState(ApplicationState.DASHBOARD);
                    break;
                }
            case R.id.nav_btc_purchase:
                {
                    if (!Application.getInstance().getState().equals(ApplicationState.PURCHASE)) {
                        mainFragment = BTCPurchaseFragment.newInstance();
                        fragmentManager.beginTransaction().replace(R.id.main_frame, mainFragment).commit();
                    }
                    Application.getInstance().setState(ApplicationState.PURCHASE);
                    break;
                }
            case R.id.nav_btc_sale:
            {
                if (!Application.getInstance().getState().equals(ApplicationState.SALE)) {
                    mainFragment = BTCSaleFragment.newInstance();
                    fragmentManager.beginTransaction().replace(R.id.main_frame, mainFragment).commit();
                }
                Application.getInstance().setState(ApplicationState.SALE);
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void initNavView() {
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView address = (TextView)header.findViewById(R.id.user_address);
        TextView user = (TextView)header.findViewById(R.id.user_name);

        address.setText(Application.getInstance().getBitcoinWalletAddress());
        user.setText(Application.getInstance().getUserPhone());
    }



    private void initOnTouchListener() {
        View view = findViewById(R.id.content_main);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    Log.i("onTouch", event.toString());
                }
                return false;
            }
        });
    }
}
