package com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCBuySell;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.simpleman.payture.bitcoinwallet.Application.Application;
import com.simpleman.payture.bitcoinwallet.CurrencyExchanger.Currency;
import com.simpleman.payture.bitcoinwallet.CurrencyExchanger.Exchanger;
import com.simpleman.payture.bitcoinwallet.R;
import com.simpleman.payture.bitcoinwallet.Utils.Tags;

import java.text.DecimalFormat;
import java.util.ArrayList;

public abstract class BTCPurchaseSaleFragment extends Fragment {

    private static String operationMode;

    private double transactionAmount;
    private double transactionCost;
    private boolean isTransactionBlockHidden = true;
    private String currencyMode;
    private static final int MAX_LENGTH = 12;

    private Spinner currencyModeSpinner;
    private EditText transactionAmountEditText;
    private Button actionButton;
    private TextView transactionCostTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currencyMode = Tags.MODE_USD_TO_BTC;
        operationMode = (String)getArguments().get(Tags.FRAGMENT_MODE);

        Application.checkBitcoinWalletAddress( getFragmentManager() );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_btc_purchase_sale, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initLabels();

        currencyModeSpinner = (Spinner)view.findViewById(R.id.btc_transaction_currency_spinner);
        setSpinnerData();
        setSpinnerListener();

        transactionAmountEditText = (EditText)view.findViewById(R.id.btc_transaction_amount);
        setEditTextListener();
        transactionAmountEditText.setText(String.valueOf(transactionAmount));

        transactionCostTextView = (TextView)view.findViewById(R.id.btc_transaction_cost);
        actionButton = (Button)view.findViewById(R.id.btc_action_button);
    }

    private void initLabels(){
        TextView transactionHeader = (TextView)getView().findViewById(R.id.btc_transaction_header);
        TextView transactionCostHeader = (TextView)getView().findViewById(R.id.btc_transaction_cost_header);
        Button actionButton = (Button)getView().findViewById(R.id.btc_action_button);

        switch (operationMode) {
            case Tags.FRAGMENT_PURCHASE_MODE: {

                transactionHeader.setText(R.string.btc_transaction_header_purchase);
                if (currencyMode.equals(Tags.MODE_USD_TO_BTC))
                    transactionCostHeader.setText(R.string.btc_transaction_cost_header_purchase_usd_to_btc);
                else if (currencyMode.equals(Tags.MODE_BTC_TO_USD))
                    transactionCostHeader.setText(R.string.btc_transaction_cost_header_purchase_btc_to_usd);

                actionButton.setText(R.string.btc_action_button_purchase);
                break;

            }
            case Tags.FRAGMENT_SALE_MODE: {

                transactionHeader.setText(R.string.btc_transaction_header_sale);

                if (currencyMode.equals(Tags.MODE_USD_TO_BTC))
                    transactionCostHeader.setText(R.string.btc_transaction_cost_header_sale_usd_to_btc);
                else if (currencyMode.equals(Tags.MODE_BTC_TO_USD))
                    transactionCostHeader.setText(R.string.btc_transaction_cost_header_sale_btc_to_usd);

                actionButton.setText(R.string.btc_action_button_sale);

            }
        }
    }

    private void setSpinnerData(){

        ArrayList<String> currencies = new ArrayList<>();

        for (Currency currency : Currency.values()) {
            currencies.add(currency.name());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.getContext(), R.layout.spinner_item,
                currencies);

        if (currencyModeSpinner != null)
            currencyModeSpinner.setAdapter(spinnerAdapter);
    }

    private void setSpinnerListener(){
        if (currencyModeSpinner != null) {
            currencyModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String selectedItem = parent.getItemAtPosition(position).toString();

                    if (selectedItem.equals(Currency.USD.name())) {
                        currencyMode = Tags.MODE_USD_TO_BTC;
                        ((TextView)(getView().findViewById(R.id.btc_transaction_cost_currency_label))).setText(R.string.currency_BTC_label);
                    } else if (selectedItem.equals(Currency.BTC.name())) {
                        currencyMode = Tags.MODE_BTC_TO_USD;
                        ((TextView)(getView().findViewById(R.id.btc_transaction_cost_currency_label))).setText(R.string.currency_USD_label);
                    }

                    initLabels();
                    renderTransactionCost();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    currencyMode = Tags.MODE_USD_TO_BTC;
                    ((TextView)(getView().findViewById(R.id.btc_transaction_cost_currency_label))).setText(R.string.currency_BTC_label);
                }
            });
        }
    }

    private void setEditTextListener(){

        if (transactionAmountEditText != null) {
            transactionAmountEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGTH)});
            transactionAmountEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    return;
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String input = s.toString();
                    try {
                        transactionAmount = Double.valueOf(input);
                    } catch (Exception ex) {
                        Log.e("BTCPurchaseFragment", ex.toString());
                        transactionAmount = 0.0;
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    renderTransactionCost();
                    toggleTransactionCostBlock();
                }
            });
        }

    }

    private void toggleTransactionCostBlock(){
        if (transactionAmountEditText != null) {
            if (isTransactionBlockHidden && transactionAmountEditText.getText().length() != 0) {
                showTransactionCostBlock();
            }

            if (!isTransactionBlockHidden && transactionAmountEditText.getText().length() == 0) {
                hideTransactionCostBlock();
            }
        }
    }

    private void showTransactionCostBlock(){
        ConstraintLayout transactionCostBlock = (ConstraintLayout)getView().findViewById(R.id.btc_transaction_cost_block);
        Animation slideUp = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
        transactionCostBlock.startAnimation(slideUp);
        transactionCostBlock.setVisibility(View.VISIBLE);
        isTransactionBlockHidden = false;
    }

    private void hideTransactionCostBlock(){
        ConstraintLayout transactionCostBlock = (ConstraintLayout)getView().findViewById(R.id.btc_transaction_cost_block);
        Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
        transactionCostBlock.startAnimation(slideDown);
        transactionCostBlock.setVisibility(View.INVISIBLE);
        isTransactionBlockHidden = true;
    }

    private void renderTransactionCost(){
        if (transactionCostTextView != null) {
            DecimalFormat decFormat = new DecimalFormat("#.00");
            switch (currencyMode) {
                case Tags.MODE_USD_TO_BTC :
                {
                    transactionCost = Exchanger.calculateBTCforUSD(transactionAmount);
                    decFormat = new DecimalFormat("#0.00000000");
                    break;
                }
                case Tags.MODE_BTC_TO_USD :
                {
                    transactionCost = Exchanger.calculateUSDforBTC(transactionAmount);
                    decFormat = new DecimalFormat("#0.00");
                    break;
                }
            }

            transactionCostTextView.setText(decFormat.format(transactionCost));
        }
    }

}
