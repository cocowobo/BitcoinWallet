package com.simpleman.payture.bitcoinwallet.Application;


import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import com.simpleman.payture.bitcoinwallet.UI.UIDialogs.WalletAddressDialogFragment;
import com.simpleman.payture.bitcoinwallet.Utils.Tags;

public class Application {

    private static Application applicationInstance;
    private Context context;

    private Application(Context context) {
        this.context = context;
        state = ApplicationState.DASHBOARD;
        getBitcoinWalletAddress();
    }

    public static synchronized Application getInstance() {
        return getInstance(null);
    }

    public static synchronized Application getInstance(Context context) {
        if (applicationInstance == null)
            applicationInstance = new Application(context);
        return applicationInstance;
    }

    /*
    * Main App Params
    * UserPhone - user phone number
    * BitcoinWalletAddress - user bitcoin wallet address
    * State - application state : DASHBOARD/PURCHASE/SALE
    */
    private String userPhone;
    private String BitcoinWalletAddress;
    private ApplicationState state;

    public ApplicationState getState() {
        return state;
    }

    public void setState(ApplicationState state) {
        this.state = state;
    }

    public String getBitcoinWalletAddress() {

        if (BitcoinWalletAddress != null)
            return BitcoinWalletAddress;

        SharedPreferences sharedPreferences = context.getSharedPreferences(Tags.WALLET_ADDRESS_FILE, Context.MODE_PRIVATE);
        BitcoinWalletAddress = sharedPreferences.getString(userPhone, null);
        return BitcoinWalletAddress;
    }

    public void setBitcoinWalletAddress(String bitcoinWalletAddress) {
        BitcoinWalletAddress = bitcoinWalletAddress;
        SharedPreferences sharedPreferences = context.getSharedPreferences(Tags.WALLET_ADDRESS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(userPhone, bitcoinWalletAddress);
        editor.commit();
    }

    public void checkBitcoinWalletAddress(FragmentManager fragmentManager) {

        if (this.getBitcoinWalletAddress() == null || this.getBitcoinWalletAddress().isEmpty()) {
            WalletAddressDialogFragment dialog = new WalletAddressDialogFragment();
            dialog.showDialog(fragmentManager);
        }
    }

    public void setUserPhone(String userPhone) {
        if (this.userPhone == null)
            this.userPhone = userPhone;
    }

    public String getUserPhone() {
        return userPhone;
    }

}
