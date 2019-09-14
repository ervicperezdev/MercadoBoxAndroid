package com.designbyte.mercadobox.orderhistory.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.orderhistory.listener.RecyclerViewOrderClickListener;

public class ViewHolderOrder extends RecyclerView.ViewHolder {
    TextView name, numProducts, folioOrder, status, total, date;
    CardView cardView;
    RecyclerViewOrderClickListener mListener;

    public ViewHolderOrder(@NonNull View itemView, RecyclerViewOrderClickListener listener) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        numProducts = itemView.findViewById(R.id.textNumProducts);
        folioOrder = itemView.findViewById(R.id.folioOrder);
        status = itemView.findViewById(R.id.statusOrder);
        total = itemView.findViewById(R.id.totalOrder);
        date = itemView.findViewById(R.id.textDate);
        mListener = listener;

    }
}
