package com.simpleman.payture.bitcoinwallet.BitcoinWallet;


import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.DeterministicSeed;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.simpleman.payture.bitcoinwallet.UI.UIActivities.Main.MainActivity;

import javax.annotation.Nullable;

/*
* Класс-контроллер для кошелька
**/
public class BitcoinWalletController {

    private Context context;
    private NetworkParameters params;
    private WalletAppKit walletAppKit;
    private BitcoinWallet bitcoinWallet;


    public BitcoinWalletController(Context context) {
        this.context = context;
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

        File walletFile = new File(context.getFilesDir(), walletFileName);

        walletAppKit = new WalletAppKit(params, walletFile, "") {
            @Override
            protected void onSetupCompleted() {
                onWalletAppKitSetup();
            }
        };

        if (params == RegTestParams.get()) {
            walletAppKit.connectToLocalHost();
        }

        /*walletAppKit.setDownloadListener(new DownloadProgressTracker(){
            @Override
            protected void doneDownload() {
                for (OnBlockchainDownloadedListener listener : listeners)
                    listener.onBlockchainDownloadedListener();
            }
        });*/

        if (seed != null) {
            walletAppKit.restoreWalletFromSeed(seed);
        }

        return;
    }

    public void start() {
        if ( walletAppKit != null )
            walletAppKit.startAsync();
    }


    public void onWalletAppKitSetup() {
        bitcoinWallet.setWallet(walletAppKit.wallet());
        broadcastWalletAppKitSetup();
    }

    private void broadcastWalletAppKitSetup() {

        if ( context != null ) {
            ((MainActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (OnWalletAppKitSetupListener listener : listeners)
                        listener.onWalletAppKitSetup();
                }
            });
        }
    }

    //private List<OnBlockchainDownloadedListener> listeners = new ArrayList<OnBlockchainDownloadedListener>();
    private List<OnWalletAppKitSetupListener> listeners = new ArrayList<OnWalletAppKitSetupListener>();


    public void addOnWalletAppKitSetupListener(OnWalletAppKitSetupListener listener) {
        listeners.add(listener);
    }


}
