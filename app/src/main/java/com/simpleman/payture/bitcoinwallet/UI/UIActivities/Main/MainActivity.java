package com.simpleman.payture.bitcoinwallet.UI.UIActivities.Main;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.simpleman.payture.bitcoinwallet.Application.Application;
import com.simpleman.payture.bitcoinwallet.Application.ApplicationState;
import com.simpleman.payture.bitcoinwallet.Application.User;
import com.simpleman.payture.bitcoinwallet.BitcoinWallet.OnProgressListener;
import com.simpleman.payture.bitcoinwallet.BitcoinWallet.OnSyncCompletedListener;
import com.simpleman.payture.bitcoinwallet.BitcoinWallet.OnWalletAppKitSetupListener;
import com.simpleman.payture.bitcoinwallet.R;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCBuySell.BTCPurchaseSaleFragment;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceChart.BTCPriceChartFragment;
import com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceInfo.BTCPriceInfoFragment;
import com.simpleman.payture.bitcoinwallet.Utils.QRCodeFormatter;
import com.simpleman.payture.bitcoinwallet.Utils.Tags;
import org.bitcoinj.core.Address;
import org.bitcoinj.uri.BitcoinURI;


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
            // если устройство было повернуто
            if (savedInstanceState.getString(Tags.DEVICE_ROTATION_EVENT) != null) {
                fragmentManager = getSupportFragmentManager();
                mainFragment = fragmentManager.getFragment(savedInstanceState, Tags.MAIN_FRAGMENT);
                infoFragment = fragmentManager.getFragment(savedInstanceState, Tags.INFO_FRAGMENT);
                Application.getInstance(this, null).setState(ApplicationState.getByCode(savedInstanceState.getInt(Tags.MAIN_STATE)));
            }

            // если был переход со страницы логина
            if (savedInstanceState.getString(Tags.PHONE) != null) {
                String passedPhone = savedInstanceState.getString(Tags.PHONE);
                User user = new User(passedPhone);
                Application.getInstance(this, user);
            }

            //иначе
        } else {
            fragmentManager = getSupportFragmentManager();
            mainFragment = new BTCPriceChartFragment();
            infoFragment = new BTCPriceInfoFragment();

            /* DEBUG */
            User user = new User("79680500275");
            Application.getInstance(this, user).setState(ApplicationState.DASHBOARD);

            //Application.getInstance(this, null).setState(ApplicationState.DASHBOARD);
        }

        // отображаем фрагменты
        fragmentManager.beginTransaction().replace(R.id.main_frame, mainFragment).commit();
        fragmentManager.beginTransaction().replace(R.id.btc_price_frame, infoFragment).commit();

        // заполняем меню
        initNavView();

        // инициализируем прогресс бар
        initProgressBar();
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
            outState.putInt(Tags.MAIN_STATE, Application.getState().ordinal());
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
                    if (!Application.getState().equals(ApplicationState.DASHBOARD)) {
                        mainFragment = new BTCPriceChartFragment();
                        fragmentManager.beginTransaction().replace(R.id.main_frame, mainFragment).commit();
                    }
                    Application.setState(ApplicationState.DASHBOARD);
                    break;
                }
            case R.id.nav_btc_purchase:
                {
                    if (!Application.getState().equals(ApplicationState.PURCHASE)) {
                        mainFragment = new BTCPurchaseSaleFragment();
                        //mainFragment = BTCPurchaseFragment.newInstance();
                        fragmentManager.beginTransaction().replace(R.id.main_frame, mainFragment).commit();
                    }
                    Application.setState(ApplicationState.PURCHASE);
                    break;
                }
            case R.id.nav_btc_sale:
            {
                if (!Application.getState().equals(ApplicationState.SALE)) {
                    mainFragment = new BTCPurchaseSaleFragment();
                    fragmentManager.beginTransaction().replace(R.id.main_frame, mainFragment).commit();
                }
                Application.setState(ApplicationState.SALE);
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

        // выводим номер пользователя
        TextView user = (TextView)header.findViewById(R.id.user_name);
        user.setText(Application.getUser().getPhone());

        // выводим адрес кошелька пользователя
        final TextView addressTextView = (TextView)header.findViewById(R.id.user_address);
        final TextView balanceTextView = (TextView)header.findViewById(R.id.user_balance_amount);
        final ImageView qrcodeImageView = (ImageView)header.findViewById(R.id.qr_image_view);

        // если кошелек уже загружен, то отображаем сразу
        if (Application.getWallet().isLoaded()) {
            String adr = Application.getWallet().getAddress().toString();
            Bitmap qrcode = Application.getWallet().getQRCodeBitmap();
            String balance = Application.getWallet().getBalance().toFriendlyString();
            addressTextView.setText(adr);
            qrcodeImageView.setImageBitmap(qrcode);
            balanceTextView.setText(balance);
        }

        // если нет, то при загрузке
        Application.getWalletController().addOnWalletAppKitSetupListener(new OnWalletAppKitSetupListener() {
            @Override
            public void onWalletAppKitSetup() {
                String adr = Application.getWallet().getAddress().toString();
                Bitmap qrcode = Application.getWallet().getQRCodeBitmap();
                addressTextView.setText(adr);
                qrcodeImageView.setImageBitmap(qrcode);
            }
        });
        
        Application.getWalletController().addOnSyncCompletedListener(new OnSyncCompletedListener() {
            @Override
            public void onSyncCompleted() {
                String balance = Application.getWallet().getBalance().toFriendlyString();
                balanceTextView.setText(balance);
            }
        });
    }

    private void initProgressBar(){
        final ProgressBar loadingBar = (ProgressBar)findViewById(R.id.loading_progress_bar);
        Application.getWalletController().addOnProgressListener(new OnProgressListener() {
            @Override
            public void onLoadingProgress(double progress) {
                loadingBar.setProgress((int)progress, true);
            }
        });

    }

}
