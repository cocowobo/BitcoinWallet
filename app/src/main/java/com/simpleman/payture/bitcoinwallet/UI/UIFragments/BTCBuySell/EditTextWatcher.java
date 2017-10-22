package com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCBuySell;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;


public abstract class EditTextWatcher implements TextWatcher, ITransactionEditText {

    EditText field1;
    EditText field2;

    public EditTextWatcher(EditText field1, EditText field2) {
        this.field1 = field1;
        this.field2 = field2;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (field1.isFocused()) {
            resetTargetValue();
            makeControlsValid();
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        setTargetValue(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (field1.isFocused()) {
            renderTargetValue();
            if (!isTransactionCostValid()) makeControlsInvalid();
        }
    }
}
