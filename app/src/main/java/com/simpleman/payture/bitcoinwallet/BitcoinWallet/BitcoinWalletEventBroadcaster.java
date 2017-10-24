package com.simpleman.payture.bitcoinwallet.BitcoinWallet;

import android.app.Activity;
import android.content.Context;

import com.simpleman.payture.bitcoinwallet.UI.UIActivities.Main.MainActivity;

import org.bitcoinj.utils.Threading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class BitcoinWalletEventBroadcaster {

    private Context context;
    private List<OnWalletAppKitSetupListener> onWalletAppKitSetupListeners;
    private List<OnProgressListener> onProgressListeners;
    private List<OnSyncCompletedListener> onSyncCompletedListeners;

    protected BitcoinWalletEventBroadcaster(Context ctx) {
        this.context = ctx;
        onWalletAppKitSetupListeners = new ArrayList<OnWalletAppKitSetupListener>();
        onProgressListeners = new ArrayList<OnProgressListener>();
        onSyncCompletedListeners = new ArrayList<OnSyncCompletedListener>();
    }

    protected void addListener(Object listener) {

        if (listener instanceof OnProgressListener)
            onProgressListeners.add((OnProgressListener) listener);

        else if (listener instanceof OnWalletAppKitSetupListener)
            onWalletAppKitSetupListeners.add((OnWalletAppKitSetupListener) listener);

        else if (listener instanceof OnSyncCompletedListener)
            onSyncCompletedListeners.add((OnSyncCompletedListener) listener);
        
        return;
    }

    protected void broadcastWalletAppKitSetup() {
        if ( context != null ) {
            ((MainActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (OnWalletAppKitSetupListener listener : onWalletAppKitSetupListeners)
                        listener.onWalletAppKitSetup();
                }
            });
        }
    }
    protected void broadcastSyncCompleted() {
        if ( context != null ) {
            ((MainActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (OnSyncCompletedListener listener : onSyncCompletedListeners)
                        listener.onSyncCompleted();
                }
            });
        }
    }

    protected void broadcastLoadProgress(final double progress) {
        if ( context != null ) {
            ((MainActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (OnProgressListener listener : onProgressListeners)
                        listener.onLoadingProgress(progress);
                }
            });
        }
    }


}
