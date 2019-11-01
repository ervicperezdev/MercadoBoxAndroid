package com.designbyte.mercadobox.orderhistory.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.models.db.Cart;
import com.designbyte.mercadobox.orderhistory.viewHolder.ItemDetailViewHolder;

import java.text.NumberFormat;
import java.util.List;

public class AdapterDetailOrder extends RecyclerView.Adapter<ItemDetailViewHolder> {
    List<Cart> items;
    Context context;
    String textExtras;
    int totalProduct, subTotalProduct;
    public AdapterDetailOrder(List<Cart> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_detail_order,parent,false);
        return new ItemDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemDetailViewHolder holder, int position) {
        holder.name.setText(String.format("%s %s(s) de %s",items.get(position).quantity, items.get(position).getUnityText(),items.get(position).name));


        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        holder.totalProduct.setText(formatter.format(items.get(position).quantity*items.get(position).costByUnit));

    }

    @Override
    public int getItemCount() {
        return items != null ? items.size():0;
    }


}
