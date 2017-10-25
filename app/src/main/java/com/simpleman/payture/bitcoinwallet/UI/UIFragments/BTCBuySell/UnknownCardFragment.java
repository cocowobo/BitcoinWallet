package com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCBuySell;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
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
import com.simpleman.payture.bitcoinwallet.Utils.IPS;

public class UnknownCardFragment extends Fragment {

    private EditText panEditText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cardinfo_new, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewComponents();
        showBlocks();
    }

    private void initViewComponents() {
        renderTransactionInfo();
        initPANEditText();
        return;
    }

    private void initPANEditText(){
        panEditText = (EditText)getView().findViewById(R.id.card_info_pan_edit_text);
        if (panEditText != null) {
            panEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String enteredPAN = s.toString();
                    updateImageView(enteredPAN);
                }
            });
        }
        return;
    }

    private void updateImageView(String pan) {
        ImageView imageView = (ImageView)getView().findViewById(R.id.card_info_ips_icon);
        if (imageView != null) {
            imageView.setImageResource(IPS.getIPSbyPAN(pan).getResource_id());
        }
        return;
    }

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

    private void showBlocks(){
        ConstraintLayout btcTransactionInfoLayout = (ConstraintLayout)getView().findViewById(R.id.btc_transaction_info_layout);
        Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);

        if (btcTransactionInfoLayout != null) {
            btcTransactionInfoLayout.startAnimation(slideDown);
            btcTransactionInfoLayout.setVisibility(View.VISIBLE);
        }

        ConstraintLayout cardInfoLayout = (ConstraintLayout)getView().findViewById(R.id.cardinfo_layout);
        if (cardInfoLayout != null) {
            cardInfoLayout.startAnimation(slideDown);
            cardInfoLayout.setVisibility(View.VISIBLE);
        }

        ImageView imageView = (ImageView)getView().findViewById(R.id.card_info_ips_icon);
        if (imageView != null) {
            imageView.startAnimation(slideDown);
            imageView.setVisibility(View.VISIBLE);
        }
    }
}
