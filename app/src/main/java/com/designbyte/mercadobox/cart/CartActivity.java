package com.designbyte.mercadobox.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.cart.adapters.AdapterProductsCart;
import com.designbyte.mercadobox.cart.listener.RecyclerViewCartClickListener;
import com.designbyte.mercadobox.models.db.Cart;
import com.designbyte.mercadobox.utils.Constants;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartView{
    ProgressBar progressBar;
    RecyclerView recyclerProductsCart;
    AdapterProductsCart adapterProductsCart;
    CartPresenter cartPresenter;
    RecyclerViewCartClickListener listener;
    TextView textViewTotalCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initViews();
        cartPresenter = new CartPresenter(this, new CartInteractor());
        listener = new RecyclerViewCartClickListener() {
            @Override
            public void onClick(View view, int position, int id) {
                if(view.getId() == R.id.deleteProduct){
                    Toast.makeText(getApplicationContext(),"Producto eliminado",Toast.LENGTH_LONG).show();
                    cartPresenter.deleteItemById(getApplicationContext(),id);
                }else if(view.getId() == R.id.less){
                    Toast.makeText(getApplicationContext(),"Menos",Toast.LENGTH_LONG).show();
                    cartPresenter.updateItemCart(Constants.PRODUCT_LESS,id,getApplicationContext());

                }else if(view.getId() == R.id.plus){
                    Toast.makeText(getApplicationContext(),"Más",Toast.LENGTH_LONG).show();
                    cartPresenter.updateItemCart(Constants.PRODUCT_PLUS,id,getApplicationContext());
                }
            }

            @Override
            public void onCostTotalChange(float total) {
                textViewTotalCart.setText(String.format("$%s",total));
            }
        };
        loadItemsCart();
    }

    public void initViews(){
        progressBar = findViewById(R.id.progressBar);
        recyclerProductsCart = findViewById(R.id.recyclerProductsCart);
        textViewTotalCart = findViewById(R.id.totalCart);
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

    @Override
    public void loadDataCart(List<Cart> items) {
        recyclerProductsCart.setLayoutManager(new LinearLayoutManager(this));
        adapterProductsCart = new AdapterProductsCart(items,this, listener);
        recyclerProductsCart.setAdapter(adapterProductsCart);
        recyclerProductsCart.setHasFixedSize(true);
        setTotalList(items);

    }

    public void setTotalList(List<Cart> items){
        float total = 0;
        for(int i= 0; i <items.size(); i++){
            total+= (items.get(i).quantity * items.get(i).costByUnit);
        }
        textViewTotalCart.setText(String.format("$ %s",total));
    }

    public void loadItemsCart(){
        cartPresenter.loadItemsCart(this);
    }


}