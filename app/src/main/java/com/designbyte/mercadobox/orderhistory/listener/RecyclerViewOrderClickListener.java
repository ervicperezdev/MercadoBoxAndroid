package com.designbyte.mercadobox.orderhistory.listener;

import android.view.View;

import com.designbyte.mercadobox.models.firebase.Order;

public interface RecyclerViewOrderClickListener {
    void onClick(View view, Order item);

}
