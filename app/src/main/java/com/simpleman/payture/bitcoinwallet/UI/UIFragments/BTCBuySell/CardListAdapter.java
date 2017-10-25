package com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCBuySell;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.simpleman.payture.bitcoinwallet.Application.RegisteredCard;
import com.simpleman.payture.bitcoinwallet.R;
import com.simpleman.payture.bitcoinwallet.Utils.IPS;

import java.util.List;

public abstract class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardListItemViewHolder> {

    private Context context;
    private List<RegisteredCard> cardList;

    public CardListAdapter(Context context, List<RegisteredCard> cards) {
        this.context = context;
        this.cardList = cards;
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    @Override
    public CardListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlist_item_layout, parent, false);

        CardListItemViewHolder holder = new CardListItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CardListItemViewHolder holder, int position) {
        final int index = position;
        String panMask = cardList.get(position).getPanMask();
        holder.cardListItemPANMask.setText(panMask);
        holder.cardListItemIPSIcon.setImageResource(IPS.getIPSbyPAN(panMask).getResource_id());
        //holder.cardListItemCardHolder.setText(cardList.get(position).getCardholder());
        // TODO: 10/25/2017 добавить вывод иконки IPS

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(index);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class CardListItemViewHolder extends RecyclerView.ViewHolder   {
        ImageView cardListItemIPSIcon;
        TextView cardListItemPANMask;
        //TextView cardListItemCardHolder;
        
        CardListItemViewHolder(View itemView) {
            super(itemView);
            cardListItemIPSIcon = (ImageView)itemView.findViewById(R.id.cardlist_item_ipsIcon);
            cardListItemPANMask = (TextView)itemView.findViewById(R.id.cardlist_item_panmask);
            //cardListItemCardHolder = (TextView)itemView.findViewById(R.id.cardlist_item_cardholder);
        }
    }

    public abstract void onItemClick(int index);

}
