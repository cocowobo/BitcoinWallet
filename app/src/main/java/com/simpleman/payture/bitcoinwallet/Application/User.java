package com.simpleman.payture.bitcoinwallet.Application;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String phone;
    private String walletFileName;
    private List<Card> cards;

    public User(String phone) {
        this.phone = phone;
        this.walletFileName = String.format("PILX_WALLET_%s", phone);
        this.cards = new ArrayList<>();
    }

    public String getWalletFileName() {
        return this.walletFileName;
    }

    public String getPhone() {
        return this.phone;
    }

    public List<Card> getCards() {
        return this.cards;
    }
}
