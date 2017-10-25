package com.simpleman.payture.bitcoinwallet.Utils;


import com.simpleman.payture.bitcoinwallet.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum IPS {

    VISA("^4[xX0-9]{0,12}(?:[0-9]{3})?$", R.drawable.card_visa),
    MASTERCARD("^(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[xX0-9]{0,12}$", R.drawable.card_mastercard),
    AMERICAN_EXPRESS("^3[47][xX0-9]{0,13}$"),
    DINERS_CLUB("^3(?:0[0-5]|[68][0-9])[xX0-9]{0,11}$"),
    DISCOVER("^6(?:011|5[0-9]{2})[xX0-9]{0,12}$"),
   // JCB("^(?:2131|1800|35\\d{3})\\d{11}$"),
    UNKNOWN("[xX0-9]{16,19}", R.drawable.card_default);

    private Pattern pattern;

    private int resource_id;

    IPS(String regex, int res){
        this.pattern = Pattern.compile(regex);
        this.resource_id = res;
    }

    IPS(String regex){
        this.pattern = Pattern.compile(regex);
        this.resource_id = R.drawable.card_default;
    }

    public int getResource_id() {
        return resource_id;
    }

    public static IPS getIPSbyPAN(String pan){
        for (IPS ips : IPS.values()) {
            Matcher matcher = ips.pattern.matcher(pan);
            if (matcher.find())
                return ips;
        }
        return UNKNOWN;
    }


}
