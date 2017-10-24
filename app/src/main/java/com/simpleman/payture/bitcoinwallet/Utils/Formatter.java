package com.simpleman.payture.bitcoinwallet.Utils;


import java.text.DecimalFormat;

public class Formatter {

    public static String formatAmount(double btcAmount) {
        DecimalFormat formatter = new DecimalFormat("#0.000000000");
        return formatter.format(btcAmount).replace(",",".");
    }

    public static String formatCost(double cost) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(cost).replace(",",".");
    }

    /*
    public static String formatPAN(String pan) {
        String res = "";
        for (int i = 0; i < pan.length(); i++) {
            res += pan.charAt(i);
            if (i!=0 && i%4 == 0)
                res += ' ';
        }
        return res;
    }*/

}
