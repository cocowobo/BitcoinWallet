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


        EditText transactionAmountEditText = (EditText)getView().findViewById(R.id.btc_sale_transaction_amount);
        EditText transactionCostEditText = (EditText)getView().findViewById(R.id.btc_sale_transaction_cost);

        EditTextWatcher textWatcher = new EditTextWatcher(transactionAmountEditText, transactionCostEditText) {

            @Override
            public void setTargetValue(String text) {
                try {
                    transactionAmount = Double.valueOf(text);
                } catch (Exception ex) {
                    Log.e("BTCPurchaseSaleFragment", "initEditTextFields " + ex.toString());
                    transactionAmount = 0.00;
                }
            }

            @Override
            public void makeControlsValid() {
                makeControlsValid();
            }

            @Override
            public void makeControlsInvalid() {
                makeControlsInvalid();
            }

            @Override
            public void renderTargetValue() {
                renderTransactionAmount();
            }

            @Override
            public void resetTargetValue() {
                resetTransactionAmount();
            }

            @Override
            public boolean isTransactionCostValid() {
                return isTransactionCostValid();
            }
        };







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

                    renderTransactionCost();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    currency = Currency.USD;
                    renderTransactionCost();
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

        DecimalFormat decFormat = new DecimalFormat("#0.00");
        switch (operationState) {
            case Tags.FRAGMENT_PURCHASE_MODE: {
                decFormat = new DecimalFormat("#0.00000000");
                break;
            }
            case Tags.FRAGMENT_SALE_MODE: {
                decFormat = new DecimalFormat("#0.00");
                break;
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

        DecimalFormat decFormat = new DecimalFormat("#0.00");
        switch (operationState) {
            case Tags.FRAGMENT_PURCHASE_MODE: {
                decFormat = new DecimalFormat("#0.00");
            }
            case Tags.FRAGMENT_SALE_MODE: {
                decFormat = new DecimalFormat("#0.00000000");
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
        Button actionButton = (Button)getView().findViewById(R.id.btc_action_button);
        actionButton.setBackgroundColor(getResources().getColor(R.color.mainBackground));
        actionButton.setEnabled(false);
    }

    private void makeControlsValid() {
        EditText transactionAmountEditText = (EditText)getView().findViewById(R.id.btc_transaction_amount);
        EditText transactionCostEditText = (EditText)getView().findViewById(R.id.btc_transaction_cost);
        transactionAmountEditText.setTextColor(Color.BLACK);
        transactionCostEditText.setTextColor(Color.BLACK);
        Button actionButton = (Button)getView().findViewById(R.id.btc_action_button);
        actionButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        actionButton.setEnabled(true);
    }

    private boolean isTransactionCostValid() {
        EditText transactionAmountEditText = (EditText)getView().findViewById(R.id.btc_transaction_amount);
        EditText transactionCostEditText = (EditText)getView().findViewById(R.id.btc_transaction_cost);

        String inputAmount = transactionAmountEditText.getText().toString();
        String inputCost  = transactionCostEditText.getText().toString();

        if (inputAmount.isEmpty() || inputCost.isEmpty())
            return false;

        if (Double.valueOf(inputAmount) * Double.valueOf(inputCost) == 0)
            return false;

        return true;
    }

}
