package com.designbyte.mercadobox.orderhistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.models.firebase.Order;
import com.designbyte.mercadobox.orderhistory.adapters.AdapterOrders;
import com.designbyte.mercadobox.orderhistory.listener.RecyclerViewOrderClickListener;

import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity implements OrderHistoryView{
    RecyclerView recyclerViewOrders;
    ProgressBar progressBar;
    RecyclerViewOrderClickListener listener;
    OrderHistoryPresenter orderHistoryPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        orderHistoryPresenter = new OrderHistoryPresenter(this,new OrderHistoryInteractor());
        initViews();
        listener = new RecyclerViewOrderClickListener() {
            @Override
            public void onClick(View view, Order item) {

            }
        };
        loadDataOrderHistory();
    }

    public void initViews(){
        recyclerViewOrders = findViewById(R.id.recyclerOrder);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void loadOrderHistory(List<Order> items) {
        recyclerViewOrders.setAdapter(new AdapterOrders(items,this,listener));
        recyclerViewOrders.setHasFixedSize(true);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));
    }

    public void loadDataOrderHistory(){
        orderHistoryPresenter.loadDataOrderHistory();
    }
}
