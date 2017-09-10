package com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceInfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.simpleman.payture.bitcoinwallet.R;
import com.simpleman.payture.bitcoinwallet.StatisticEngine.BackgroundTasks.GetBTCCurrentPriceTask;


public class BTCPriceInfoFragment extends Fragment {

    private ImageButton updateBTCPriceInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_btc_price_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateBTCPriceInfo = (ImageButton) view.findViewById(R.id.update_btc_price_info_button);

        asyncUpdateBTCPriceInfo();

        updateBTCPriceInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncUpdateBTCPriceInfo();
            }
        });
    }

    private void asyncUpdateBTCPriceInfo(){
        startAnimation();
        new GetBTCCurrentPriceTask(this).execute();
    }

    private void startAnimation(){
        Animation rotation = AnimationUtils.loadAnimation(getContext(), R.anim.rotation);
        rotation.setRepeatCount(Animation.INFINITE);
        updateBTCPriceInfo.startAnimation(rotation);
    }

    public void stopAnimation(){
        updateBTCPriceInfo.clearAnimation();
    }

    public void fillFields(){

    }
    private void clearFields(){

    }



}
