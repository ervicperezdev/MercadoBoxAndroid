package com.designbyte.mercadobox.cart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.cart.adapters.AdapterProductsCart;
import com.designbyte.mercadobox.cart.listener.RecyclerViewCartClickListener;
import com.designbyte.mercadobox.detailorder.DetailActivity;
import com.designbyte.mercadobox.models.db.Cart;
import com.designbyte.mercadobox.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartView{
    ProgressBar progressBar;
    RecyclerView recyclerProductsCart;
    AdapterProductsCart adapterProductsCart;
    CartPresenter cartPresenter;
    RecyclerViewCartClickListener listener;
    TextView textViewTotalCart;
    Button btnContinue;
    Context context;

    final static int ORDER_COMPLETED = 111;
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
                    Toast.makeText(getApplicationContext(),"Producto eliminado",Toast.LENGTH_SHORT).show();
                    cartPresenter.deleteItemById(getApplicationContext(),id);
                }else if(view.getId() == R.id.less){
                    cartPresenter.updateItemCart(Constants.PRODUCT_LESS,id,getApplicationContext());

                }else if(view.getId() == R.id.plus){
                    cartPresenter.updateItemCart(Constants.PRODUCT_PLUS,id,getApplicationContext());
                }
            }

            @Override
            public void onCostTotalChange(float total) {
                textViewTotalCart.setText(String.format("$%s",total));
            }
        };
        loadItemsCart();
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDetailOrder();
            }
        });
    }

    public void initViews(){
        progressBar = findViewById(R.id.progressBar);
        recyclerProductsCart = findViewById(R.id.recyclerProductsCart);
        textViewTotalCart = findViewById(R.id.totalCart);
        btnContinue = findViewById(R.id.btnContinue);
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

    @Override
    public void onRemoveSuccess() {
        List<Cart> cartList = new ArrayList<>();

        adapterProductsCart = new AdapterProductsCart(cartList,this, listener);
        recyclerProductsCart.setAdapter(adapterProductsCart);
        recyclerProductsCart.setHasFixedSize(true);
        setTotalList(cartList);
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


    public void goToDetailOrder(){
        Intent intent = new Intent(CartActivity.this,DetailActivity.class);
        intent.putExtra("total",textViewTotalCart.getText().toString());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivityForResult(intent,ORDER_COMPLETED, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }else{
            startActivityForResult(intent,ORDER_COMPLETED);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == ORDER_COMPLETED) {
                setResult(RESULT_OK);
                finish();
            }
        }else{

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.removeAll:
                cartPresenter.removeAllProducts(context);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
