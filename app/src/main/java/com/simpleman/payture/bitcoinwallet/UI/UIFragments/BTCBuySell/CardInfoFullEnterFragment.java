package com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCBuySell;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.simpleman.payture.bitcoinwallet.Application.Application;
import com.simpleman.payture.bitcoinwallet.R;
import com.simpleman.payture.bitcoinwallet.Utils.Formatter;
import com.simpleman.payture.bitcoinwallet.Utils.Formatter;

public class CardInfoFullEnterFragment extends Fragment {

    private EditText PANEditText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showBlocks();

        renderTransactionInfo();
        initCardInfoFields();
        tryToFillFields();

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
        transactionAmount.setText(Formatter.formatAmount(Application.getCurrentTransaction().getTransactionAmount()));
        transactionCost.setText(String.format("%s %s",
                Formatter.formatCost(Application.getCurrentTransaction().getTransactionCost()),
                Application.getCurrentTransaction().getCurrency().name()));
    }

    private void initCardInfoFields(){
        initPANEditText();
    }

    private void initPANEditText() {
        PANEditText = (EditText)getView().findViewById(R.id.card_info_pan_edit_text);
        PANEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void tryToFillFields() {

        if (Application.getUser().getCards() != null && Application.getUser().getCards().size() != 0) {
            // show dialog
        }
        return;
    }

    private void showBlocks() {
        ConstraintLayout cardInfoLayout = (ConstraintLayout)getView().findViewById(R.id.cardinfo_layout);
        Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
        cardInfoLayout.startAnimation(slideDown);
        cardInfoLayout.setVisibility(View.VISIBLE);

        ConstraintLayout transactionInfoLayout = (ConstraintLayout)getView().findViewById(R.id.btc_transaction_info_layout);
        transactionInfoLayout.startAnimation(slideDown);
        transactionInfoLayout.setVisibility(View.VISIBLE);

        ImageView ipsIcon = (ImageView)getView().findViewById(R.id.card_info_ips_icon);
        ipsIcon.startAnimation(slideDown);
        ipsIcon.setVisibility(View.VISIBLE);
    }

    private void hideBlocks(){
        ConstraintLayout cardInfoLayout = (ConstraintLayout)getView().findViewById(R.id.btc_transaction_cost_block);
        Animation slideUp = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
        cardInfoLayout.startAnimation(slideUp);
        cardInfoLayout.setVisibility(View.INVISIBLE);

        ConstraintLayout transactionInfoLayout = (ConstraintLayout)getView().findViewById(R.id.btc_transaction_info_layout);
        transactionInfoLayout.startAnimation(slideUp);
        transactionInfoLayout.setVisibility(View.INVISIBLE);

        ImageView ipsIcon = (ImageView)getView().findViewById(R.id.card_info_ips_icon);
        ipsIcon.startAnimation(slideUp);
        ipsIcon.setVisibility(View.INVISIBLE);
    }
}
