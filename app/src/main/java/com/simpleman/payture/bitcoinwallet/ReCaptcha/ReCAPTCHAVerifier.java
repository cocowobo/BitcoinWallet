package com.simpleman.payture.bitcoinwallet.ReCaptcha;


import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public abstract class ReCAPTCHAVerifier {

    private static final String APP_KEY = "6LeTqDAUAAAAALkgpFt7DI7Qt1MB58oIye3C3ww-";

    public ReCAPTCHAVerifier(Context context){
        SafetyNet.getClient(context).verifyWithRecaptcha(APP_KEY)
                .addOnSuccessListener(new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                    @Override
                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse recaptchaTokenResponse) {
                        onSuccessAction(recaptchaTokenResponse);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        onFailureAction(e);
                    }
                });
    }

    public abstract void onSuccessAction(SafetyNetApi.RecaptchaTokenResponse recaptchaTokenResponse);
    public abstract void onFailureAction(Exception e);

}
