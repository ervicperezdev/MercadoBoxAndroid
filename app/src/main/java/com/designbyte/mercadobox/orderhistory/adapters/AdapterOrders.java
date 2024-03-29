package com.designbyte.mercadobox.orderhistory.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.main.viewHolder.ViewHolderProduct;
import com.designbyte.mercadobox.models.db.Cart;
import com.designbyte.mercadobox.models.firebase.Order;
import com.designbyte.mercadobox.orderhistory.listener.RecyclerViewOrderClickListener;
import com.designbyte.mercadobox.orderhistory.viewHolder.ViewHolderOrder;

import java.text.NumberFormat;
import java.util.List;

public class AdapterOrders extends RecyclerView.Adapter<ViewHolderOrder> {
    List<Order> orderList;
    Context context;
    RecyclerViewOrderClickListener mListener;

    public AdapterOrders(List<Order> orderList, Context context, RecyclerViewOrderClickListener mListener) {
        this.orderList = orderList;
        this.context = context;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolderOrder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_list_history,parent,false);
        return new ViewHolderOrder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderOrder holder, final int position) {
        holder.name.setText(orderList.get(position).name);
        holder.folioOrder.setText(String.format("Pedido %s",orderList.get(position).idOrder));
        holder.date.setText(orderList.get(position).date);
        holder.status.setText(String.format("%s",orderList.get(position).convertStatusToText()));
        holder.total.setText(String.format("Total %s",calculaTotal(orderList.get(position).products)));
        holder.numProducts.setText(String.format("Articulos %s",orderList.get(position).products.size()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mListener.onClick(v, orderList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderList!=null?orderList.size():0;
    }

    private String calculaTotal(List<Cart> items){
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        float total = 0;
        for (Cart item : items
             ) {
            total += (item.quantity*item.costByUnit);
        }
        return "$"+formatter.format(total);
    }
}
