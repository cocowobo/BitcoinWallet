package com.simpleman.payture.bitcoinwallet.Application;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.util.Log;

import com.simpleman.payture.bitcoinwallet.BitcoinWallet.BitcoinWallet;
import com.simpleman.payture.bitcoinwallet.BitcoinWallet.BitcoinWalletController;

import java.io.IOException;

public class Application {

    private static Application applicationInstance;
    private Context context;

    private Application(Context context, User usr) {

        this.context = context;
        user = usr;
        state = ApplicationState.DASHBOARD;
        walletController = new BitcoinWalletController(context);
        wallet = walletController.getBitcoinWallet();

        try {
            walletController.setupWalletAppKit(null, user.getWalletFileName());
        } catch ( IOException ex ) {
            Log.e("Application", "setupWalletAppKit - " + ex.toString());
        }

        this.walletController.start();
    }

    public static Application getInstance(Context context, User user) {
        if (applicationInstance == null) {
            applicationInstance = new Application(context, user);
        }
        return applicationInstance;
    }

    private static ApplicationState state;
    private static User user;
    private static BitcoinWallet wallet;

    private static BitcoinWalletController walletController;

    public static ApplicationState getState() {
        return state;
    }

    public static void setState(ApplicationState s) {
        state = s;
    }

    public static User getUser() {
        return user;
    }

    public static BitcoinWallet getWallet() {
        return wallet;
    }

    public static BitcoinWalletController getWalletController() {
        return walletController;
    }



    public void checkBitcoinWalletAddress(FragmentManager fragmentManager) {
        return;/*
        if (this.getBitcoinWalletAddress() == null || this.getBitcoinWalletAddress().isEmpty()) {
            WalletAddressDialogFragment dialog = new WalletAddressDialogFragment();
            dialog.showDialog(fragmentManager);
        }*/
    }

}
