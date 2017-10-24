package com.simpleman.payture.bitcoinwallet.BitcoinWallet;


import org.bitcoinj.core.*;
import org.bitcoinj.core.listeners.DownloadProgressTracker;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.DeterministicSeed;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import android.content.Context;
import android.util.Log;

import javax.annotation.Nullable;

/*
* Класс-контроллер для кошелька
**/
public class BitcoinWalletController {

    private Context context;
    private NetworkParameters params;
    private WalletAppKit walletAppKit;
    private BitcoinWallet bitcoinWallet;

    private BitcoinWalletEventBroadcaster broadcaster;


    public BitcoinWalletController(Context context) {
        this.context = context;
        this.broadcaster = new BitcoinWalletEventBroadcaster(context);
        this.params = TestNet3Params.get();
        this.bitcoinWallet = new BitcoinWallet();
    }

    public WalletAppKit getWalletAppKit() {
        return walletAppKit;
    }

    public BitcoinWallet getBitcoinWallet() {
        return bitcoinWallet;
    }

    public void setupWalletAppKit(@Nullable DeterministicSeed seed, String walletFileName) throws IOException {

        File walletFile = new File(context.getFilesDir().getPath());

        walletAppKit = new WalletAppKit(params, walletFile, walletFileName) {
            @Override
            protected void onSetupCompleted() {
                bitcoinWallet.setWallet(walletAppKit.wallet());
                broadcaster.broadcastWalletAppKitSetup();
                wallet().allowSpendingUnconfirmedTransactions();
            }
        };

        if (params == RegTestParams.get()) {
            walletAppKit.connectToLocalHost();
        }

        walletAppKit.setDownloadListener(new DownloadProgressTracker(){
            @Override
            protected void progress(double pct, int blocksSoFar, Date date) {
                super.progress(pct, blocksSoFar, date);
                Log.i("Blockchain Download", "Progress: " + pct + " % blocks so far: " + blocksSoFar);
                broadcaster.broadcastLoadProgress(pct);
            }

            @Override
            protected void doneDownload() {
                super.doneDownload();
                broadcaster.broadcastSyncCompleted();
            }
        }).setBlockingStartup(false).setUserAgent("PILX", "1.0");;


        if (seed != null) {
            walletAppKit.restoreWalletFromSeed(seed);
        }

        return;
    }

    public void start() {
        if ( walletAppKit != null )
            walletAppKit.startAsync();
    }


    public void addOnWalletAppKitSetupListener(OnWalletAppKitSetupListener listener) {
        broadcaster.addListener(listener);
    }

    public void addOnProgressListener(OnProgressListener listener) {
        broadcaster.addListener(listener);
    }

    public void addOnSyncCompletedListener(OnSyncCompletedListener listener) {
        broadcaster.addListener(listener);
    }
}
