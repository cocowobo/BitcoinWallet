package com.simpleman.payture.bitcoinwallet.Application;


import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import com.simpleman.payture.bitcoinwallet.UI.UIDialogs.WalletAddressDialogFragment;
import com.simpleman.payture.bitcoinwallet.Utils.Tags;

public class Application {

    private static Application applicationInstance;
    private static Context context;

    private Application(Context context) {
        this.context = context;
        state = ApplicationState.DASHBOARD;
        getBitcoinWalletAddress();
    }

    public static synchronized Application getInstance(Context context) {
        if (applicationInstance == null)
            applicationInstance = new Application(context);
        return applicationInstance;
    }

    private static String BitcoinWalletAddress;
    private static ApplicationState state;


    public static ApplicationState getState() {
        return state;
    }

    public static void setState(ApplicationState state) {
        Application.state = state;
    }


    public static String getBitcoinWalletAddress() {

        if (BitcoinWalletAddress != null)
            return BitcoinWalletAddress;

        SharedPreferences sharedPreferences = context.getSharedPreferences(Tags.WALLET_ADDRESS_FILE, Context.MODE_PRIVATE);
        BitcoinWalletAddress = sharedPreferences.getString(Tags.BITCOIN_WALLET_ADDRESS, null);
        return BitcoinWalletAddress;
    }

    public static void setBitcoinWalletAddress(String bitcoinWalletAddress) {
        BitcoinWalletAddress = bitcoinWalletAddress;
        SharedPreferences sharedPreferences = context.getSharedPreferences(Tags.WALLET_ADDRESS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Tags.BITCOIN_WALLET_ADDRESS, bitcoinWalletAddress);
        editor.commit();
    }

    public static void checkBitcoinWalletAddress(FragmentManager fragmentManager) {

        if (Application.getBitcoinWalletAddress() == null) {
            WalletAddressDialogFragment dialog = new WalletAddressDialogFragment();
            dialog.show(fragmentManager, Tags.WALLET_ADDRESS_DIALOG);
        }
    }
}
