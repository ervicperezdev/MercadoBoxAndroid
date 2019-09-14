package com.designbyte.mercadobox.orderhistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.designbyte.mercadobox.R;

public class OrderHistoryActivity extends AppCompatActivity {
    RecyclerView recyclerViewOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
    }
}
