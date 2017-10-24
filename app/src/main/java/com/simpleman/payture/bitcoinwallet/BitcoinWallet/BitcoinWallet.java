package com.simpleman.payture.bitcoinwallet.BitcoinWallet;


import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.simpleman.payture.bitcoinwallet.Utils.QRCodeFormatter;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.utils.Threading;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.listeners.WalletChangeEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class BitcoinWallet {

    private Bitmap qrCodeBitmap;
    private Address address;
    private Coin balance;

    public BitcoinWallet() {}

    public BitcoinWallet(Wallet wallet) {
        setWallet(wallet);
    }

    public final void setWallet(Wallet wallet) {

        wallet.addChangeEventListener(Threading.USER_THREAD, new WalletChangeEventListener() {
            @Override
            public void onWalletChanged(Wallet wallet) {
                update(wallet);
            }
        });

        update(wallet);
        this.qrCodeBitmap = QRCodeFormatter.encodeTextToBitmap(wallet.currentReceiveAddress().toString(), 300);
    }

    private void update(Wallet wallet) {
        this.balance = wallet.getBalance();
        this.address = wallet.currentReceiveAddress();
    }

    public Address getAddress() {
        return address;
    }

    public Coin getBalance() {
        return balance;
    }

    public Bitmap getQRCodeBitmap() {
        return qrCodeBitmap;
    }

    public boolean isLoaded() {
        return (address != null && balance != null && qrCodeBitmap != null);
    }
}
