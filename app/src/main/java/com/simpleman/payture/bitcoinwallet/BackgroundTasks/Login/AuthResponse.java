package com.simpleman.payture.bitcoinwallet.BackgroundTasks.Login;

public class AuthResponse {
    private boolean success;

    public AuthResponse(boolean success){
        this.success = success;
    }

    public boolean isSuccess(){
        return this.success;
    }
}
