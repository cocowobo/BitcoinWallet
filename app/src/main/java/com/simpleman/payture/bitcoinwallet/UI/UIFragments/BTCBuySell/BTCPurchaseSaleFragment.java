package com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCBuySell;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

import com.simpleman.payture.bitcoinwallet.Application.Application;
import com.simpleman.payture.bitcoinwallet.Application.PaytureTransaction;
import com.simpleman.payture.bitcoinwallet.Application.PaytureTransactionMode;
import com.simpleman.payture.bitcoinwallet.CurrencyExchanger.Currency;
import com.simpleman.payture.bitcoinwallet.R;
import com.simpleman.payture.bitcoinwallet.UI.UIActivities.Main.MainActivity;
import com.simpleman.payture.bitcoinwallet.Utils.Tags;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BTCPurchaseSaleFragment extends Fragment {

    // Основные элементы фрагмента
    // Схожие элементы на разных вкладках повторяются
    private TabHost tabHost;

    // Вкладка КУПИТЬ
    private EditText transactionPurchaseAmountEditText;
    private EditText transactionPurchaseCostEditText;
    private Spinner currencyPurchaseSpinner;
    private Button purchaseButton;

    // Вкладка ПРОДАТЬ
    private EditText transactionSaleAmountEditText;
    private EditText transactionSaleCostEditText;
    private Spinner currencySaleSpinner;
    private Button saleButton;


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

        if (savedInstanceState != null) {
            String mode = savedInstanceState.getString(Tags.TAG_TRANSACTION_MODE);
            String currency = savedInstanceState.getString(Tags.TAG_CURRENCY);
            double cost = savedInstanceState.getDouble(Tags.TAG_TRANSACTION_COST);
            double amount = savedInstanceState.getDouble(Tags.TAG_TRANSACTION_AMOUNT);
            Application.getCurrentTransaction().restoreTransaction(mode, amount, cost, currency);
        }
        initViewComponents();
        return;
    }

    // Инициализация основных компонентов
    private void initViewComponents() {
        initTabHost();
        initTabComponents(Tab.PURCHASE);
        initTabComponents(Tab.SALE);
        return;
    }

    // Инициализация основных компонентов вкладки
    private void initTabComponents(Tab tab) {
        initCurrencySpinner(tab);
        initEditTextFields(tab);
        initActionButton(tab);
        return;
    }

    /**
     * Инициализация TabHost
     * Задаются layout-ы вкладок покупки/продажи
     * Устанавливается дефолтная вкладка при открытии
     **/
    private void initTabHost() {
        this.tabHost = (TabHost)getView().findViewById(R.id.tab_host);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec(Tab.PURCHASE.name());
        tabSpec.setContent(R.id.btc_purchase_tab_layout);
        tabSpec.setIndicator(getResources().getString(R.string.btc_transaction_header_purchase));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(Tab.SALE.name());
        tabSpec.setContent(R.id.btc_sale_tab_layout);
        tabSpec.setIndicator(getResources().getString(R.string.btc_transaction_header_sale));
        tabHost.addTab(tabSpec);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Log.i("BTCPurchaseSaleFragment", "onTabChanged - newTab - " + tabId);

                if ( tabId == Tab.PURCHASE.name() ) {
                    resetTabComponents(Tab.SALE);
                    Application.resetCurrentTransaction();
                    Application.getCurrentTransaction().setTransactionMode(PaytureTransactionMode.PURCHASE);
                    Application.getCurrentTransaction().setCurrency(Currency.USD);
                }
                else if ( tabId == Tab.SALE.name() ) {
                    resetTabComponents(Tab.PURCHASE);
                    Application.resetCurrentTransaction();
                    Application.getCurrentTransaction().setTransactionMode(PaytureTransactionMode.SALE);
                    Application.getCurrentTransaction().setCurrency(Currency.USD);
                }
            }
        });

        tabHost.setCurrentTab(0);
        return;
    }

    /**
     * Инициализируется поведение полей для ввода суммы / стоимости операции
     * Входной параметр : идентификатор вкладки, на которой находятся поля для ввода
     * Суть : при изменении значения в первом поле происходит сброс, пересчет, сохранение, отображение
     * связанного значения во втором поле. И наоборот.
     */
    private void initEditTextFields(final Tab tab) {

        EditText transactionAmountEditText;
        EditText transactionCostEditText;

        if ( tab == Tab.PURCHASE ) {
            this.transactionPurchaseAmountEditText = (EditText)getView().findViewById(R.id.btc_purchase_transaction_amount);
            this.transactionPurchaseCostEditText = (EditText)getView().findViewById(R.id.btc_purchase_transaction_cost);
            transactionAmountEditText = this.transactionPurchaseAmountEditText;
            transactionCostEditText = this.transactionPurchaseCostEditText;
        } else if ( tab == Tab.SALE ) {
            this.transactionSaleAmountEditText = (EditText)getView().findViewById(R.id.btc_sale_transaction_amount);
            this.transactionSaleCostEditText = (EditText)getView().findViewById(R.id.btc_sale_transaction_cost);
            transactionAmountEditText = this.transactionSaleAmountEditText;
            transactionCostEditText = this.transactionSaleCostEditText;
        } else {
            Log.e("BTCPurchaseSaleFragment", "error: initEditTextFields - invalid tab parameter ");
            return;
        }

        if ( transactionAmountEditText == null || transactionCostEditText == null ) {
            Log.e("BTCPurchaseSaleFragment", "error: initEditTextFields - one of edit text fields is null ");
            return;
        }

        TransactionEditTextWatcher amountEditTextWatcher = new TransactionEditTextWatcher(transactionAmountEditText, transactionCostEditText, false) {
            @Override
            public void clearTargetValue() {
                Application.getCurrentTransaction().setTransactionCost(0.0);
                this.renderTargetValue();
                return;
            }

            @Override
            public void renderTargetValue() {
                this.getTargetField().setText(getRenderFormat().format(Application.getCurrentTransaction().getTransactionCost()).replace(',', '.'));
                return;
            }

            @Override
            public void saveBasicValue(String inputBasic) {
                try {
                    double value = Double.valueOf(inputBasic.replace(',', '.'));
                    Application.getCurrentTransaction().setTransactionAmount(value);
                } catch ( Exception ex ) {
                    Log.e("BTCPurchaseSaleFragment", "TransactionEditTextWatcher - saveBasicValue - " + ex.toString() );
                    Application.getCurrentTransaction().setTransactionAmount(0.0);
                }
                return;
            }

            @Override
            public void makeAdditionalControlsValid() {
                enableActionButton();
            }

            @Override
            public void makeAdditionalControlsInvalid() {
                disableActionButton();
            }
        };

        TransactionEditTextWatcher costEditTextWatcher = new TransactionEditTextWatcher(transactionCostEditText, transactionAmountEditText, true) {
            @Override
            public void clearTargetValue() {
                Application.getCurrentTransaction().setTransactionAmount(0.00);
                this.renderTargetValue();
                return;
            }

            @Override
            public void renderTargetValue() {
                this.getTargetField().setText(getRenderFormat().format(Application.getCurrentTransaction().getTransactionAmount()).replace(',', '.'));
                return;
            }

            @Override
            public void saveBasicValue(String inputBasic) {
                try {
                    double value = Double.valueOf(inputBasic.replace(',', '.'));
                    Application.getCurrentTransaction().setTransactionCost(value);
                } catch ( Exception ex ) {
                    Log.e("BTCPurchaseSaleFragment", "TransactionEditTextWatcher - saveBasicValue - " + ex.toString() );
                    Application.getCurrentTransaction().setTransactionCost(0.0);
                }
                return;
            }

            @Override
            public void makeAdditionalControlsValid() {
                enableActionButton();
            }

            @Override
            public void makeAdditionalControlsInvalid() {
                disableActionButton();
            }
        };

        transactionAmountEditText.addTextChangedListener( amountEditTextWatcher );
        transactionCostEditText.addTextChangedListener( costEditTextWatcher );
        return;
    }

    /*
    * Инициализируется поведение спиннера
    * При изменении значения выполняется пересчет значения Amount поля PaytureTransaction и отображение
    * */
    private void initCurrencySpinner(Tab tab) {
        Spinner currencySpinner;
        if ( tab == Tab.PURCHASE ) {
            this.currencyPurchaseSpinner = (Spinner)getView().findViewById(R.id.btc_purchase_spinner);
            currencySpinner = this.currencyPurchaseSpinner;
        } else if ( tab == Tab.SALE ) {
            this.currencySaleSpinner = (Spinner)getView().findViewById(R.id.btc_sale_spinner);
            currencySpinner = this.currencySaleSpinner;
        } else {
            return;
        }

        if ( currencySpinner == null )
            return;

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
                    DecimalFormat decimalFormat = new DecimalFormat("#0.000000000");
                    Currency currency = Currency.valueOf(parent.getItemAtPosition(position).toString());
                    Application.getCurrentTransaction().setCurrency(currency);

                    if ( Application.getCurrentTransaction().getTransactionMode() == PaytureTransactionMode.PURCHASE &&
                            transactionPurchaseAmountEditText != null &&
                            transactionPurchaseAmountEditText.getText().length() != 0) {
                        transactionPurchaseAmountEditText.setText(decimalFormat.format(Application.getCurrentTransaction().getTransactionAmount()));
                    }

                    if ( Application.getCurrentTransaction().getTransactionMode() == PaytureTransactionMode.SALE &&
                            transactionSaleAmountEditText != null &&
                            transactionSaleAmountEditText.getText().length() != 0) {
                        transactionSaleAmountEditText.setText(decimalFormat.format(Application.getCurrentTransaction().getTransactionAmount()));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    DecimalFormat decimalFormat = new DecimalFormat("#0.000000000");
                    Currency currency = Currency.USD;
                    Application.getCurrentTransaction().setCurrency(currency);
                    if ( Application.getCurrentTransaction().getTransactionMode() == PaytureTransactionMode.PURCHASE &&
                            transactionPurchaseAmountEditText != null) {
                        transactionPurchaseAmountEditText.setText(decimalFormat.format(Application.getCurrentTransaction().getTransactionAmount()));
                    }

                    if ( Application.getCurrentTransaction().getTransactionMode() == PaytureTransactionMode.SALE &&
                            transactionSaleAmountEditText != null) {
                        transactionSaleAmountEditText.setText(decimalFormat.format(Application.getCurrentTransaction().getTransactionAmount()));
                    }
                }
            });
        }
        return;
    }

    private void initActionButton(Tab tab) {
        Button actionButton;
        if ( tab == Tab.PURCHASE ) {
            this.purchaseButton = (Button)getView().findViewById(R.id.btc_purchase_action_button);
            actionButton = this.purchaseButton;
        } else if ( tab == Tab.SALE ) {
            this.saleButton = (Button)getView().findViewById(R.id.btc_sale_action_button);
            actionButton = this.saleButton;
        } else {
            return;
        }

        if (actionButton != null) {
            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*String text = "Operation : " + paytureTransaction.getTransactionMode().toString() + "\n";
                    text += "Amount : " + paytureTransaction.getTransactionAmount() + " BTC \n";
                    text += "Cost : " + paytureTransaction.getTransactionCost() + " " + paytureTransaction.getCurrency().toString();
                    Toast.makeText(getContext(), text, Toast.LENGTH_SHORT ).show();*/
                    if ( getActivity() instanceof MainActivity) {
                        ((MainActivity)getActivity()).openCardInfoEnterFragment();
                    }
                }
            });
        }

    }
    /**
     * Сброс значений полей формы
     */
    private void resetTabComponents(Tab tab) {
        if ( tab == Tab.PURCHASE ) {
            if ( this.transactionPurchaseAmountEditText != null )this.transactionPurchaseAmountEditText.setText("");
            if ( this.transactionPurchaseCostEditText != null ) this.transactionPurchaseCostEditText.setText("");
            if ( this.currencyPurchaseSpinner != null ) this.currencyPurchaseSpinner.setSelection(0);
        } else if ( tab == Tab.SALE ) {
            if ( this.transactionSaleAmountEditText != null ) this.transactionSaleAmountEditText.setText("");
            if ( this.transactionSaleCostEditText != null )this.transactionSaleCostEditText.setText("");
            if ( this.currencySaleSpinner != null ) this.currencySaleSpinner.setSelection(0);
        } else {
            Log.e("BTCPurchaseSaleFragment", "error: initEditTextFields - invalid tab parameter ");
            return;
        }
        return;
    }

    /*
    * Активирует / деактивирует кнопку начала транзакции
    **/
    private void disableActionButton() {
        if ( tabHost.getCurrentTab() ==  Tab.PURCHASE.ordinal() ) {
            this.purchaseButton.setBackgroundColor(getResources().getColor(R.color.mainBackground));
            this.purchaseButton.setEnabled(false);
        } else if ( tabHost.getCurrentTab() ==  Tab.SALE.ordinal() ) {
            this.saleButton.setBackgroundColor(getResources().getColor(R.color.mainBackground));
            this.saleButton.setEnabled(false);
        }
        return;
    }

    private void enableActionButton() {
        if ( tabHost.getCurrentTab() ==  Tab.PURCHASE.ordinal() ) {
            this.purchaseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            this.purchaseButton.setEnabled(true);
        } else if ( tabHost.getCurrentTab() ==  Tab.SALE.ordinal() ) {
            this.saleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            this.saleButton.setEnabled(true);
        }
        return;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(Tags.TAG_TRANSACTION_AMOUNT, Application.getCurrentTransaction().getTransactionAmount());
        outState.putDouble(Tags.TAG_TRANSACTION_COST, Application.getCurrentTransaction().getTransactionCost());
        outState.putString(Tags.TAG_CURRENCY, Application.getCurrentTransaction().getCurrency().toString());
        outState.putString(Tags.TAG_TRANSACTION_MODE, Application.getCurrentTransaction().getTransactionMode().toString());
    }
}
