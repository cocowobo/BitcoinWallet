package com.simpleman.payture.bitcoinwallet.Application;


import com.simpleman.payture.bitcoinwallet.CurrencyExchanger.Currency;
import com.simpleman.payture.bitcoinwallet.CurrencyExchanger.Exchanger;
import com.simpleman.payture.bitcoinwallet.Utils.Tags;

public class PaytureTransaction {

    private PaytureTransactionMode transactionMode = PaytureTransactionMode.PURCHASE;     // Принимаются значения Purchase/Sale
    private double transactionAmount = 0.0;     // Кол-во BTC в транзакции
    private double transactionCost = 0.0;       // Стоимость покупки/продажи биткоинов
    private Currency currency = Currency.USD;      // текущая валюта при операции

    public PaytureTransaction(){}

    public PaytureTransaction(PaytureTransactionMode mode, Currency currency) {
        this.transactionMode = mode;
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
        this.transactionAmount = this.calculateTransactionAmount();
    }

    public void setCurrency(String currency) {
        this.currency = Currency.getByName(currency);
        this.transactionAmount = this.calculateTransactionAmount();
    }

    public PaytureTransactionMode getTransactionMode() {
        return transactionMode;
    }

    public void setTransactionMode(String transactionMode) {
        this.transactionMode = PaytureTransactionMode.getByName(transactionMode);
    }

    public void setTransactionMode(PaytureTransactionMode transactionMode) {
        this.transactionMode = transactionMode;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
        this.transactionCost = this.calculateTransactionCost();
    }

    public double getTransactionCost() {
        return transactionCost;
    }

    public void setTransactionCost(double transactionCost) {
        this.transactionCost = transactionCost;
        this.transactionAmount = this.calculateTransactionAmount();
    }

    private double calculateTransactionCost() {
        return Exchanger.getInstance().calculateForward(transactionAmount, currency.name());
    }

    private double calculateTransactionAmount() {
        return Exchanger.getInstance().calculateReverse(transactionCost, currency.name());
    }

    public void restoreTransaction(String transactionMode, double transactionAmount, double transactionCost, String currency) {
        this.transactionAmount = transactionAmount;
        this.transactionCost = transactionCost;
        this.transactionMode = PaytureTransactionMode.getByName(transactionMode);
        this.currency = Currency.getByName(currency);
        return;
    }
}
