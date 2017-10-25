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
import android.widget.Button;
import android.widget.ImageView;
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
    private RecyclerView cardlistRecyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private Button cancelButton;
    private Button newCardButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cardinfo_list, container, false);
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
        cardlistRecyclerView = (RecyclerView)getView().findViewById(R.id.card_info_cardlist_recyclerview);
        layoutManager = new LinearLayoutManager(getContext());

        recyclerViewAdapter = new CardListAdapter(getContext(), cards){
            @Override
            public void onItemClick(int index) {
                //((MainActivity)getActivity()).openConfirmCardFragment(cards.get(index));
            }
        };

        if (cardlistRecyclerView != null) {
            cardlistRecyclerView.setLayoutManager(layoutManager);
            cardlistRecyclerView.setAdapter(recyclerViewAdapter);
        }
        return;
    }

    // Test method!!!
    private List<RegisteredCard> initTestData() {
        List<RegisteredCard> list = new ArrayList<>();
        list.add(new RegisteredCard("a6d24342-e93e-4b42-ae76-626bfa03e158", "411111xxxxxx1112", "Ivan Ivanov"));
        list.add(new RegisteredCard("19d25532-10fa-bb21-ae76-62ccfa13e174", "411111xxxxxx1111", "Peter Ivanov"));
        list.add(new RegisteredCard("a6d24342-e93e-4b42-ae76-626bfa03e158", "411111xxxxxx1112", "Ivan Ivanov"));
        list.add(new RegisteredCard("19d25532-10fa-bb21-ae76-62ccfa13e174", "411111xxxxxx1111", "Peter Ivanov"));
        list.add(new RegisteredCard("a6d24342-e93e-4b42-ae76-626bfa03e158", "411111xxxxxx1112", "Ivan Ivanov"));
        list.add(new RegisteredCard("19d25532-10fa-bb21-ae76-62ccfa13e174", "411111xxxxxx1111", "Peter Ivanov"));
        list.add(new RegisteredCard("a6d24342-e93e-4b42-ae76-626bfa03e158", "411111xxxxxx1112", "Ivan Ivanov"));
        list.add(new RegisteredCard("19d25532-10fa-bb21-ae76-62ccfa13e174", "411111xxxxxx1111", "Peter Ivanov"));
        list.add(new RegisteredCard("a6d24342-e93e-4b42-ae76-626bfa03e158", "411111xxxxxx1112", "Ivan Ivanov"));
        list.add(new RegisteredCard("19d25532-10fa-bb21-ae76-62ccfa13e174", "411111xxxxxx1111", "Peter Ivanov"));
        list.add(new RegisteredCard("a6d24342-e93e-4b42-ae76-626bfa03e158", "411111xxxxxx1112", "Ivan Ivanov"));
        list.add(new RegisteredCard("19d25532-10fa-bb21-ae76-62ccfa13e174", "411111xxxxxx1111", "Peter Ivanov"));
        list.add(new RegisteredCard("a6d24342-e93e-4b42-ae76-626bfa03e158", "411111xxxxxx1112", "Ivan Ivanov"));
        list.add(new RegisteredCard("19d25532-10fa-bb21-ae76-62ccfa13e174", "411111xxxxxx1111", "Peter Ivanov"));
        list.add(new RegisteredCard("a6d24342-e93e-4b42-ae76-626bfa03e158", "411111xxxxxx1112", "Ivan Ivanov"));
        list.add(new RegisteredCard("19d25532-10fa-bb21-ae76-62ccfa13e174", "411111xxxxxx1111", "Peter Ivanov"));
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

