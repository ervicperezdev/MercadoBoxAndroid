package com.designbyte.mercadobox.cart.listener;

import android.view.View;

public interface RecyclerViewCartClickListener {
    void onClick(View view, int position, int id);
    void onCostTotalChange(float total);
}
