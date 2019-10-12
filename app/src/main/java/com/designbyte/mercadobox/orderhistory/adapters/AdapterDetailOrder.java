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
        holder.name.setText(items.get(position).name);
        totalProduct = 0;
        subTotalProduct = 0;

        holder.quantity.setText(String.format("%sX",items.get(position).quantity));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size():0;
    }


}
