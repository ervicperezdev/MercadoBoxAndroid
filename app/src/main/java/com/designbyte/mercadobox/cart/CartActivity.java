package com.designbyte.mercadobox.cart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.designbyte.mercadobox.R;

public class CartActivity extends AppCompatActivity implements CartView{
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initViews();
    }

    public void initViews(){
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void onContinueOrder() {

    }

    @Override
    public void showProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        progressBar.setVisibility(View.GONE);
    }
}
