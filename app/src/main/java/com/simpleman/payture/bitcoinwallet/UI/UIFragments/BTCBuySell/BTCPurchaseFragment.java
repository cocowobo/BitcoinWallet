package com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCBuySell;

import android.os.Bundle;

import com.simpleman.payture.bitcoinwallet.Utils.Tags;

public class BTCPurchaseFragment extends BTCPurchaseSaleFragment {

    public static BTCPurchaseFragment newInstance() {
        BTCPurchaseFragment fragment = new BTCPurchaseFragment();
        Bundle args = new Bundle();
        args.putString(Tags.FRAGMENT_MODE, Tags.FRAGMENT_PURCHASE_MODE);
        fragment.setArguments(args);
        return fragment;
    }

}
