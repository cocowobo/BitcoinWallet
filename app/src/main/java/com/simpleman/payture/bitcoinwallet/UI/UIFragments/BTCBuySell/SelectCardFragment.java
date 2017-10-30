package com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCBuySell;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.simpleman.payture.bitcoinwallet.Application.Application;
import com.simpleman.payture.bitcoinwallet.Application.RegisteredCard;
import com.simpleman.payture.bitcoinwallet.R;
import com.simpleman.payture.bitcoinwallet.UI.UIActivities.Main.MainActivity;
import com.simpleman.payture.bitcoinwallet.Utils.Formatter;

import java.util.ArrayList;
import java.util.List;

public class SelectCardFragment extends Fragment {

    //private EditText PANEditText;

    private Spinner cardlistSpinner;

    private Button cancelButton;
    private Button newCardButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cardinfo_latest, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initCardInfoRecyclerView();
        showBlocks();
        initButtons();
        renderTransactionInfo();
    }

    /**
     * Выводим информацию о транзакции
     * в соответствующие поля
     */
    private void renderTransactionInfo() {
        TextView transactionHeader = (TextView)getView().findViewById(R.id.btc_transaction_info_header);
        TextView transactionAmount = (TextView)getView().findViewById(R.id.btc_transaction_info_amount);
        TextView transactionCost = (TextView)getView().findViewById(R.id.btc_transaction_info_cost);

        transactionHeader.setText(Application.getCurrentTransaction().getTransactionMode().name());
        transactionAmount.setText(String.format("%s BTC",
                Formatter.formatAmount(Application.getCurrentTransaction().getTransactionAmount())));
        transactionCost.setText(String.format("%s %s",
                Formatter.formatCost(Application.getCurrentTransaction().getTransactionCost()),
                Application.getCurrentTransaction().getCurrency().name()));
    }

    private void initCardInfoRecyclerView() {
        final List<RegisteredCard> cards = initTestData(); // = Application.getUser().getCards();

        cardlistSpinner = (Spinner)getView().findViewById(R.id.card_info_select_card_spinner);
        CardListAdapterLatest adapter = new CardListAdapterLatest(cards, getContext());
        if (adapter.getCount() <= 1) cardlistSpinner.setClickable(false);
        cardlistSpinner.setAdapter(adapter);
        cardlistSpinner.setSelection(0);
        cardlistSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final int real_index = position - 1;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return;
    }

    // Test method!!!
    private List<RegisteredCard> initTestData() {
        List<RegisteredCard> list = new ArrayList<>();
        list.add(new RegisteredCard("a6d24342-e93e-4b42-ae76-626bfa03e158", "411111xxxxxx1111", "Ivan Ivanov"));
        list.add(new RegisteredCard("19d25532-10fa-bb21-ae76-62ccfa13e174", "555111xxxxxx1123", "Peter Ivanov"));
        return list;
    }

    private void initButtons(){
        cancelButton = (Button)getView().findViewById(R.id.card_info_cancel_button);
        if (cancelButton != null) {
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelTransaction();
                }
            });
        }

        newCardButton = (Button)getView().findViewById(R.id.card_info_add_card_button);
        if (newCardButton != null) {
            newCardButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)getActivity()).openUnknownCardFragment();
                }
            });
        }
    }


    private void cancelTransaction() {
        Application.resetCurrentTransaction();
        hideBlocks();
        ((MainActivity)getActivity()).openPurchaseSaleFragment();
        return;
    }

    private void showBlocks() {
        ConstraintLayout btcTransactionInfoLayout = (ConstraintLayout)getView().findViewById(R.id.btc_transaction_info_layout);
        Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);

        if (btcTransactionInfoLayout != null) {
            btcTransactionInfoLayout.startAnimation(slideDown);
            btcTransactionInfoLayout.setVisibility(View.VISIBLE);
        }

        RecyclerView cardListView = (RecyclerView)getView().findViewById(R.id.card_info_cardlist_recyclerview);
        if (cardListView != null) {
            cardListView.startAnimation(slideDown);
            cardListView.setVisibility(View.VISIBLE);
        }
    }

    private void hideBlocks(){
        Animation slideUp = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
        ConstraintLayout transactionInfoLayout = (ConstraintLayout)getView().findViewById(R.id.btc_transaction_info_layout);
        if (transactionInfoLayout != null) {
            transactionInfoLayout.startAnimation(slideUp);
            transactionInfoLayout.setVisibility(View.INVISIBLE);
        }

        RecyclerView cardListView = (RecyclerView)getView().findViewById(R.id.card_info_cardlist_recyclerview);
        if (cardListView != null) {
            cardListView.startAnimation(slideUp);
            cardListView.setVisibility(View.INVISIBLE);
        }
    }
}

