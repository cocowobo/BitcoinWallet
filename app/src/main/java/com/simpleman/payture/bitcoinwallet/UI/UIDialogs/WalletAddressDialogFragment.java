package com.simpleman.payture.bitcoinwallet.UI.UIDialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.simpleman.payture.bitcoinwallet.Application.Application;
import com.simpleman.payture.bitcoinwallet.Application.ApplicationState;
import com.simpleman.payture.bitcoinwallet.R;
import com.simpleman.payture.bitcoinwallet.Utils.Tags;

public class WalletAddressDialogFragment extends DialogFragment {

    private EditText walletAddressEditText;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        this.setCancelable(false);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = inflater.inflate(R.layout.dialog_wallet_address, null);

        builder.setTitle(R.string.wallet_address_dialog_title)
                .setView(view)
                .setPositiveButton(R.string.dialog_button_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        walletAddressEditText = (EditText) view.findViewById(R.id.dialog_wallet_address_editText);
                        //Application.getInstance().setBitcoinWalletAddress(walletAddressEditText.getText().toString());
                    }
                });

        if (!Application.getState().equals(ApplicationState.SALE))
            builder.setNegativeButton(R.string.dialog_button_later, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    WalletAddressDialogFragment.this.getDialog().cancel();
                }
            });

        return builder.create();
    }

    public void showDialog(FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag(Tags.WALLET_ADDRESS_DIALOG);
        if (prev != null) {
            transaction.remove(prev);
        }
        this.show(transaction, Tags.WALLET_ADDRESS_DIALOG);
    }

}
