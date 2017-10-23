package com.simpleman.payture.bitcoinwallet.Application;

public class User {

    private String phone;
    private String walletFileName;

    public User(String phone) {
        this.phone = phone;
        this.walletFileName = String.format("PILX_WALLET_%s", phone);
    }

    public String getWalletFileName() {
        return this.walletFileName;
    }

    public String getPhone() {
        return this.phone;
    }
}
