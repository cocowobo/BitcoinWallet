package com.simpleman.payture.bitcoinwallet.Application;

public class RegisteredCard {

    private String id;
    private String panMask;
    private String cardholder;
    private String status;
    private boolean noCVV;
    private boolean isExpired;

    public RegisteredCard(String id, String panMask, String cardholder) {
        this.id = id;
        this.panMask = panMask;
        this.cardholder = cardholder;
    }

    public String getId() {
        return id;
    }

    public String getPanMask() {
        return panMask;
    }

    public String getCardholder() {
        return cardholder;
    }

}
