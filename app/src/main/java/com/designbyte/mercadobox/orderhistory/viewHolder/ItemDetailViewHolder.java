package com.designbyte.mercadobox.orderhistory.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.designbyte.mercadobox.R;

public class ItemDetailViewHolder extends RecyclerView.ViewHolder {
    public TextView name, totalProduct;

    public ItemDetailViewHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.tvName);
        totalProduct = itemView.findViewById(R.id.totalProduct);
    }


}