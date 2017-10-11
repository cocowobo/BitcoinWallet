package com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCBuySell;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import com.simpleman.payture.bitcoinwallet.CurrencyExchanger.Currency;
import com.simpleman.payture.bitcoinwallet.CurrencyExchanger.Exchanger;
import com.simpleman.payture.bitcoinwallet.R;
import com.simpleman.payture.bitcoinwallet.Utils.Tags;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class BTCPurchaseSaleFragmentNew extends Fragment {

    private String operationState;

    private Currency currency = Currency.USD;
    private double transactionAmount = 0.00;
    private double transactionCost = 0.00;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_btc_purchase_sale_new, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        initTabHost();
        initEditTextFields();
        initCurrencySpinner();
    }

    private void initTabHost() {
        TabHost tabHost = (TabHost)getView().findViewById(R.id.tab_host);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec(Tags.FRAGMENT_PURCHASE_MODE);
        tabSpec.setContent(R.id.btc_purchase_tab_layout);
        tabSpec.setIndicator(getResources().getString(R.string.btc_transaction_header_purchase));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(Tags.FRAGMENT_SALE_MODE);
        tabSpec.setContent(R.id.btc_sale_tab_layout);
        tabSpec.setIndicator(getResources().getString(R.string.btc_transaction_header_sale));
        tabHost.addTab(tabSpec);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                operationState = tabId;
            }
        });

        tabHost.setCurrentTab(0);
        operationState = Tags.FRAGMENT_PURCHASE_MODE;
    }

    private void initEditTextFields() {
        final EditText transactionAmountEditText = (EditText)getView().findViewById(R.id.btc_transaction_amount);
        final EditText transactionCostEditText = (EditText)getView().findViewById(R.id.btc_transaction_cost);

        transactionAmountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (transactionAmountEditText.isFocused()) {
                    resetTransactionCost();
                    makeControlsValid();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String textTransactionAmount = s.toString();
                try {
                    transactionAmount = Double.valueOf(textTransactionAmount);
                } catch (Exception ex) {
                    Log.e("BTCPurchaseSaleFragment", "initEditTextFields " + ex.toString());
                    transactionAmount = 0.00;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (transactionAmountEditText.isFocused()) {
                    renderTransactionCost();
                    if (!isTransactionCostValid()) makeControlsInvalid();
                }
            }
        });

        transactionCostEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (transactionCostEditText.isFocused()) {
                    resetTransactionAmount();
                    makeControlsValid();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String textTransactionAmount = s.toString();
                try {
                    transactionCost = Double.valueOf(textTransactionAmount);
                } catch (Exception ex) {
                    Log.e("BTCPurchaseSaleFragment", "initEditTextFields " + ex.toString());
                    transactionCost = 0.00;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (transactionCostEditText.isFocused()) {
                    renderTransactionAmount();
                    if (!isTransactionCostValid()) makeControlsInvalid();
                }
            }
        });
    }

    private void initCurrencySpinner() {
        Spinner currencySpinner = (Spinner)getView().findViewById(R.id.spinner);
        ArrayList<String> currencies = new ArrayList<>();

        for (Currency currency : Currency.values()) {
            currencies.add(currency.name());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.getContext(), R.layout.spinner_item,
                currencies);

        if (currencySpinner != null) {
            currencySpinner.setAdapter(spinnerAdapter);

            currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    currency = Currency.valueOf(parent.getItemAtPosition(position).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    currency = Currency.USD;
                }
            });
        }
    }


    /* if we need to calculate transaction cost by amount */

    private void resetTransactionCost() {
        this.transactionCost = 0.00;
        renderTransactionCost();
    }

    private void renderTransactionCost() {
        transactionCost = calculateTransactionCost();
        EditText transactionCostEditText = (EditText)getView().findViewById(R.id.btc_transaction_cost);

        DecimalFormat decFormat = new DecimalFormat("#.00");
        switch (operationState) {
            case Tags.FRAGMENT_PURCHASE_MODE: {
                decFormat = new DecimalFormat("#.00000000");
            }
            case Tags.FRAGMENT_SALE_MODE: {
                decFormat = new DecimalFormat("#.00");
            }
        }

        transactionCostEditText.setText(decFormat.format(transactionCost));
    }

    private double calculateTransactionCost() {
        switch (operationState) {
            case Tags.FRAGMENT_PURCHASE_MODE: {
                return Exchanger.getInstance().calculateReverse(transactionAmount, currency.name());
            }
            case Tags.FRAGMENT_SALE_MODE: {
                return Exchanger.getInstance().calculateForward(transactionAmount, currency.name());
            }
            default:
                return 0;
        }
    }


    /* if we need to calculate transaction amount by cost */

    private void resetTransactionAmount() {
        this.transactionAmount = 0.00;
        renderTransactionAmount();
    }

    private void renderTransactionAmount() {
        transactionAmount = calculateTransactionAmount();
        EditText transactionAmountEditText = (EditText)getView().findViewById(R.id.btc_transaction_amount);


        DecimalFormat decFormat = new DecimalFormat("#.00");
        switch (operationState) {
            case Tags.FRAGMENT_PURCHASE_MODE: {
                decFormat = new DecimalFormat("#.00");
            }
            case Tags.FRAGMENT_SALE_MODE: {
                decFormat = new DecimalFormat("#.00000000");
            }
        }
        transactionAmountEditText.setText(decFormat.format(transactionAmount));
    }

    private double calculateTransactionAmount() {
        switch (operationState) {
            case Tags.FRAGMENT_PURCHASE_MODE: {
                return Exchanger.getInstance().calculateForward(transactionCost, currency.name());
            }
            case Tags.FRAGMENT_SALE_MODE: {
                return Exchanger.getInstance().calculateReverse(transactionCost, currency.name());
            }
            default:
                return 0;
        }
    }


    /* validation styles */

    private void makeControlsInvalid() {
        EditText transactionAmountEditText = (EditText)getView().findViewById(R.id.btc_transaction_amount);
        EditText transactionCostEditText = (EditText)getView().findViewById(R.id.btc_transaction_cost);
        transactionAmountEditText.setTextColor(Color.RED);
        transactionCostEditText.setTextColor(Color.RED);
        Button actionButton = (Button)getView().findViewById(R.id.action_button);
        actionButton.setEnabled(false);
    }

    private void makeControlsValid() {
        EditText transactionAmountEditText = (EditText)getView().findViewById(R.id.btc_transaction_amount);
        EditText transactionCostEditText = (EditText)getView().findViewById(R.id.btc_transaction_cost);
        transactionAmountEditText.setTextColor(Color.BLACK);
        transactionCostEditText.setTextColor(Color.BLACK);
        Button actionButton = (Button)getView().findViewById(R.id.btc_action_button);
        actionButton.setEnabled(true);
    }

    private boolean isTransactionCostValid() {
        EditText transactionAmountEditText = (EditText)getView().findViewById(R.id.btc_transaction_amount);
        EditText transactionCostEditText = (EditText)getView().findViewById(R.id.btc_transaction_cost);

        String inputAmount = transactionAmountEditText.getText().toString();
        String inputCost  = transactionCostEditText.getText().toString();


        if (Double.valueOf(inputAmount) == 0 && Double.valueOf(inputCost) != 0)
            return false;
        return true;
    }

}
