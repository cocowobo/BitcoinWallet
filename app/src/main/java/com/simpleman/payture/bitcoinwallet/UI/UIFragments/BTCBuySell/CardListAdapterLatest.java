package com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCBuySell;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.simpleman.payture.bitcoinwallet.Application.RegisteredCard;
import com.simpleman.payture.bitcoinwallet.R;
import com.simpleman.payture.bitcoinwallet.Utils.IPS;

import java.util.ArrayList;
import java.util.List;

public class CardListAdapterLatest extends BaseAdapter {

    private List<RegisteredCard> cardList;
    private LayoutInflater inflater;

    public CardListAdapterLatest(List<RegisteredCard> cards, Context context) {
        this.cardList = initDataList(cards);
        this.inflater = LayoutInflater.from(context);
    }

    private List<RegisteredCard> initDataList(List<RegisteredCard> list){
        List<RegisteredCard> cards = new ArrayList<>();
        cards.add(null); // null element for hint rendering
        for (RegisteredCard card : list) {
            cards.add(card);
        }
        return cards;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int index = position;
        if (position == 0) {
            convertView = inflater.inflate(R.layout.cardlist_hint_item, null);
        } else {
            convertView = inflater.inflate(R.layout.cardlist_item_layout, null);
            ImageView cardListItemIPSIcon = (ImageView)convertView.findViewById(R.id.cardlist_item_ipsIcon);
            TextView cardListItemPANMask = (TextView)convertView.findViewById(R.id.cardlist_item_panmask);
            String panMask = cardList.get(index).getPanMask();

            cardListItemPANMask.setText(panMask);
            cardListItemIPSIcon.setImageResource(IPS.getIPSbyPAN(panMask).getResource_id());
        }
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }

    @Override
    public int getCount() {
        int count = cardList.size();
        return count;
    }
}
