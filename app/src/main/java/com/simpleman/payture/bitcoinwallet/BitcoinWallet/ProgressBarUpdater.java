package com.simpleman.payture.bitcoinwallet.BitcoinWallet;


import org.bitcoinj.core.listeners.DownloadProgressTracker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProgressBarUpdater extends DownloadProgressTracker {
    @Override
    protected void progress(double pct, int blocksLeft, Date date) {
        super.progress(pct, blocksLeft, date);
    }





}
