package com.designbyte.mercadobox.orderhistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.models.firebase.Order;
import com.designbyte.mercadobox.orderhistory.adapters.AdapterDetailOrder;
import com.designbyte.mercadobox.orderhistory.adapters.AdapterOrders;
import com.designbyte.mercadobox.orderhistory.listener.RecyclerViewOrderClickListener;

import java.text.NumberFormat;
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
                showDialogDetailOrder(item);
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

    public void showDialogDetailOrder(Order order){
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);

        final android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_detail_cost_order,null);

        TextView tvSubtotal = view.findViewById(R.id.tvSubtotal);
        TextView tvIVA = view.findViewById(R.id.tvIVA);
        TextView tvTotal = view.findViewById(R.id.tvTotal);
        TextView tvCerrar = view.findViewById(R.id.tvCerrar);


        tvSubtotal.setText(String.format("%s",formatter.format(order.getTotal())));
        tvIVA.setText(String.format("%s",formatter.format(order.getIVA())));
        tvTotal.setText(String.format("%s",formatter.format(order.getTotal()+order.getIVA())));

        RecyclerView recyclerView1 = (RecyclerView) view.findViewById(R.id.listDetailProduct);
        recyclerView1.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(lm);
        AdapterDetailOrder adapter = new AdapterDetailOrder(order.products,this);
        recyclerView1.setAdapter(adapter);
        dialog.setView(view);
        dialog.setCancelable(false);
        final Dialog dialog1 = dialog.show();
        tvCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
    }


}
