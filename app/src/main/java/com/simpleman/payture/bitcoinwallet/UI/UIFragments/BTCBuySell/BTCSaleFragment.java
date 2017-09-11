package com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCBuySell;

import android.os.Bundle;
import com.simpleman.payture.bitcoinwallet.Utils.Tags;

public class BTCSaleFragment extends BTCPurchaseSaleFragment {

    public static BTCSaleFragment newInstance() {
        BTCSaleFragment fragment = new BTCSaleFragment();
        Bundle args = new Bundle();
        args.putString(Tags.FRAGMENT_MODE, Tags.FRAGMENT_SALE_MODE);
        fragment.setArguments(args);
        return fragment;
    }


}
