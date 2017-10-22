package com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCBuySell;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;

/*
* Абстрактный класс, управляющий поведением двух полей для ввода информации о транзакции:
* 1) поле кол-во товаров
* 2) поле стоимости товаров
* Вход конструктора:
* BasicField - то поле, с которым взаимодействуем
* TargetField - то поле, на которое оказывается влияние
* isTargetBTC - флаг, определяющий формат отображения числа (точность)
*       если isTargetBTC=true то в поле TargetField будет точность до 9 знаков
* */
public abstract class TransactionEditTextWatcher implements TextWatcher, ITransactionEditText {

    private EditText basicField;
    private EditText targetField;
    private DecimalFormat decimalFormat;

    public EditText getBasicField() {
        return basicField;
    }

    public DecimalFormat getRenderFormat() {
        return decimalFormat;
    }

    public EditText getTargetField() {
        return targetField;
    }

    public TransactionEditTextWatcher(EditText basicField, EditText targetField, boolean isTargetBTC) {
        this.basicField = basicField;
        this.targetField = targetField;

        if ( isTargetBTC )
            decimalFormat = new DecimalFormat("#0.000000000");
        else
            decimalFormat = new DecimalFormat("#0.00");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (basicField.isFocused()) {
            clearTargetValue();
            makeControlsValid();
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (basicField.isFocused())
            saveBasicValue(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (basicField.isFocused()) {
            renderTargetValue();
            if (!isTransactionCostValid()) makeControlsInvalid();
        }
    }

    public boolean isTransactionCostValid() {
        String inputBasic = this.getBasicField().getText().toString();
        String inputTarget  = this.getTargetField().getText().toString();

        if (inputBasic.isEmpty() || inputTarget.isEmpty())
            return false;

        if (Double.valueOf(inputBasic) * Double.valueOf(inputTarget) == 0)
            return false;

        return true;
    }

    public void makeControlsValid() {
        this.getBasicField().setTextColor(Color.BLACK);
        this.getTargetField().setTextColor(Color.BLACK);
        makeAdditionalControlsValid();
    }

    public void makeControlsInvalid() {
        this.getBasicField().setTextColor(Color.RED);
        this.getTargetField().setTextColor(Color.RED);
        makeAdditionalControlsInvalid();
    }

}
