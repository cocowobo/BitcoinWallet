package com.simpleman.payture.bitcoinwallet.UI.UIActivities.Login;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.simpleman.payture.bitcoinwallet.BackgroundTasks.Login.AuthResponse;
import com.simpleman.payture.bitcoinwallet.BackgroundTasks.Login.GetPhoneCodeTask;
import com.simpleman.payture.bitcoinwallet.BackgroundTasks.Login.IAsyncAuthCallback;
import com.simpleman.payture.bitcoinwallet.BackgroundTasks.Login.SubmitPhoneCodeTask;
import com.simpleman.payture.bitcoinwallet.R;
import com.simpleman.payture.bitcoinwallet.ReCaptcha.ReCAPTCHAVerifier;
import com.simpleman.payture.bitcoinwallet.Utils.Tags;

public class LoginActivity extends AppCompatActivity implements IAsyncAuthCallback {

    private static LoginState state;
    private Button actionButton;
    private EditText inputField;

    private IAsyncAuthCallback authCallback;

    private String phone;
    private String code;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        state = LoginState.GET_PHONE_CODE_STATE;
        actionButton = (Button)findViewById(R.id.action_button);
        inputField = (EditText)findViewById(R.id.login_input_field);
        authCallback = this;

        initUIElements();
    }

    private void initUIElements() {

        inputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (state == LoginState.GET_PHONE_CODE_STATE) {
                    phone = s.toString();
                } else if (state == LoginState.GET_AUTH_TOKEN_STATE) {
                    code = s.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        switch (state) {
            case GET_PHONE_CODE_STATE: {
                actionButton.setText(R.string.login_action_button_state_get_code);
                inputField.setText("");
                inputField.setHint(R.string.login_hint_state_get_code);

                actionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new ReCAPTCHAVerifier(getApplicationContext()){

                            @Override
                            public void onSuccessAction(SafetyNetApi.RecaptchaTokenResponse recaptchaTokenResponse){
                                if (!recaptchaTokenResponse.getTokenResult().isEmpty()) {
                                    phone = inputField.getText().toString();
                                    token = recaptchaTokenResponse.getTokenResult();
                                    GetPhoneCodeTask task = new GetPhoneCodeTask(getApplicationContext(), authCallback);
                                    task.execute(phone, token);
                                } else {
                                    showError(LoginError.RECAPTCHA_ERROR);
                                }
                            }
                            @Override
                            public void onFailureAction(Exception e){
                                Log.e("Exception", e.toString());
                                showError(LoginError.RECAPTCHA_ERROR);
                            }
                        };
                    }
                });

                break;
            }
            case GET_AUTH_TOKEN_STATE: {
                actionButton.setText(R.string.login_action_button_state_get_token);
                inputField.setText("");
                inputField.setHint(R.string.login_hint_state_get_token);

                actionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new ReCAPTCHAVerifier(getApplicationContext()){
                            @Override
                            public void onSuccessAction(SafetyNetApi.RecaptchaTokenResponse recaptchaTokenResponse){
                                if (!recaptchaTokenResponse.getTokenResult().isEmpty()) {
                                    code = inputField.getText().toString();
                                    token = recaptchaTokenResponse.getTokenResult();
                                    SubmitPhoneCodeTask task = new SubmitPhoneCodeTask(getApplicationContext(), authCallback);
                                    task.execute(phone, code, token);
                                } else {
                                    showError(LoginError.RECAPTCHA_ERROR);
                                }
                            }
                            @Override
                            public void onFailureAction(Exception e){
                                Log.e("Exception", e.toString());
                                showError(LoginError.RECAPTCHA_ERROR);
                            }
                        };
                    }
                });

                break;
            }
        }
    }

    private void showError(String error){
        Snackbar.make(this.findViewById(R.id.login_activity_layout), error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Tags.LOGIN_STATE, state.name());

    }

    @Override
    public void onGetCodeResult(AuthResponse result) {
        if (result.isSuccess()) {
            this.state = LoginState.GET_AUTH_TOKEN_STATE;
            initUIElements();
        }
    }

    @Override
    public void onSubmitCodeResult(AuthResponse result) {
       if (result.isSuccess()) {
           Log.i("onSubmitCodeResult" , "starting new activity...");
           // start new activity
       }
    }

}
