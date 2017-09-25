package com.simpleman.payture.bitcoinwallet.BackgroundTasks.Login;

public interface IAsyncAuthCallback {
    void onGetCodeResult(AuthResponse getCodeResult);
    void onSubmitCodeResult(AuthResponse submitCodeResult);
}
