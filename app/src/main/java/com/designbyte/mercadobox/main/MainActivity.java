package com.designbyte.mercadobox.main;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.cart.CartActivity;
import com.designbyte.mercadobox.main.adapters.AdapterCategories;
import com.designbyte.mercadobox.main.listener.RecyclerViewProductClickListener;
import com.designbyte.mercadobox.models.ResponseCategories;
import com.designbyte.mercadobox.models.db.Cart;
import com.designbyte.mercadobox.models.firebase.Category;
import com.designbyte.mercadobox.models.firebase.Product;
import com.designbyte.mercadobox.orderhistory.OrderHistoryActivity;
import com.designbyte.mercadobox.profile.ProfileActivity;
import com.designbyte.mercadobox.splash.SplashActivity;
import com.designbyte.mercadobox.utils.Constants;
import com.designbyte.mercadobox.utils.MercadoBoxPreferences;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {
    FirebaseDatabase database;
    DatabaseReference categories;
    ProgressBar progressBar;
    DrawerLayout drawer;
    NavigationView navigationView;
    MainPresenter mainPresenter;
    MercadoBoxPreferences mercadoBoxPreferences;
    RecyclerView recyclerCategories;
    RecyclerViewProductClickListener listener;
    CardView btnCart;
    TextView textTotalCart, productCount;
    final static int ORDER_COMPLETED = 111;
    Activity activity;
    AppCompatEditText tvSearch;
    AdapterCategories adapterCategories;
    Context context;
    MainInteractor mainInteractor;
    TextView nameHeader, emailHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        navigationView.setNavigationItemSelectedListener(this);
        mainInteractor = new MainInteractor();
        mainInteractor.context = this;
        mercadoBoxPreferences = new MercadoBoxPreferences(this);
        mainPresenter = new MainPresenter(this,mainInteractor);
        database = FirebaseDatabase.getInstance();
        activity = this;
        context = this;
        listener = new RecyclerViewProductClickListener() {
            @Override
            public void onClick(View view, int position, int idCategory) {
                if(view.getId() == R.id.btnAdd){

                    updateCart(Constants.PRODUCT_ADD,idCategory,position);
                    Toast toast = Toast.makeText(MainActivity.this,String.format("Producto agregado al carrito"),Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }else if(view.getId() == R.id.less){
                    updateCart(Constants.PRODUCT_LESS,idCategory,position);
                    showButtonCart();

                    //Toast.makeText(MainActivity.this,String.format("less %s",idCategory),Toast.LENGTH_LONG).show();
                }else if(view.getId() == R.id.plus){
                    updateCart(Constants.PRODUCT_PLUS,idCategory,position);
                    showButtonCart();

                    //Toast.makeText(MainActivity.this,String.format("plus %s",idCategory),Toast.LENGTH_LONG).show();
                }
            }

        };

        loadDataCategories();
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivityForResult(new Intent(MainActivity.this, CartActivity.class), ORDER_COMPLETED, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                }else{
                    startActivityForResult(new Intent(MainActivity.this, CartActivity.class), ORDER_COMPLETED);

                }
            }
        });
        showButtonCart();

    }

    public void initViews(){
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        progressBar = findViewById(R.id.progressBar);
        recyclerCategories = findViewById(R.id.recyclerCategories);
        btnCart = findViewById(R.id.btnCart);
        productCount = findViewById(R.id.productCount);
        textTotalCart = findViewById(R.id.textTotalCart);
        tvSearch = findViewById(R.id.tvSearch);
        nameHeader = findViewById(R.id.nameHeader);
        emailHeader = findViewById(R.id.emailHeader);
        tvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Gson gson = new Gson();
                ResponseCategories data = gson.fromJson(mercadoBoxPreferences.readSharedSetting("categories",""), ResponseCategories.class);

                String textSearch = s.toString().toLowerCase();
                Log.e("TextChanged",textSearch);
                if(s.toString().length() <= 0){
                    Log.e("TextChangedVacio","hola");

                    adapterCategories.updateList(data.categories);
                }else {
                    List<Category> aux = new ArrayList<>();

                    for (Category category : data.categories) {
                        List<Product> productAux = new ArrayList<>();
                        Category categoryAux = null;
                        for (Product itemProduc : category.products) {
                            if (itemProduc.name.toLowerCase().contains(textSearch)) {
                                productAux.add(itemProduc);
                                categoryAux = category;
                            }

                        }
                        if (categoryAux != null) {
                            categoryAux.products = productAux;
                            aux.add(categoryAux);

                        }
                    }
                    adapterCategories.updateList(aux);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            goToProfile();
        } else if (id == R.id.nav_orderhistory) {
            goToHistory();
        }  else if (id == R.id.nav_logout) {
            logout();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goToProfile(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class),ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }else{
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));

        }
    }
    public void goToHistory(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(new Intent(MainActivity.this, OrderHistoryActivity.class),ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }else{
            startActivity(new Intent(MainActivity.this, OrderHistoryActivity.class));

        }
    }
    public void goToAddress(){
        //startActivity(new Intent(MainActivity.this, AddressActivity.class));
    }
    public void goToPreferences(){
        //startActivity(new Intent(MainActivity.this, PreferencesActivity.class));
    }

    public void logout(){
        mainPresenter.logout();
        mercadoBoxPreferences.saveSharedSetting("logged",false);
        mercadoBoxPreferences.saveSharedSetting("email","");
        mercadoBoxPreferences.saveSharedSetting("passwd","");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(new Intent(MainActivity.this,SplashActivity.class),ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }else{
            startActivity(new Intent(MainActivity.this,SplashActivity.class));

        }
        finish();
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
    public void loadDataCategories(final List<Category> items) {

        adapterCategories = new AdapterCategories(items,this,listener);
        recyclerCategories.setAdapter(adapterCategories);
        recyclerCategories.setLayoutManager(new LinearLayoutManager(this));
        recyclerCategories.setHasFixedSize(true);

    }

    @Override
    public void showButtonBottomCart(List<Cart> items) {
        btnCart.setVisibility(View.VISIBLE);
        if(items != null)
        textTotalCart.setText(calculaTotal(items));
        productCount.setText(String.format("%s",items != null ? items.size():""));
    }

    @Override
    public void hideButtonBottomCart() {
        btnCart.setVisibility(View.GONE);
    }

    @Override
    public void updateRecycler() {

    }


    public String calculaTotal(List<Cart> items){
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);

        float total = 0;

        for (Cart item: items
             ) {
            total += (item.quantity*item.costByUnit);
        }
        return "$"+formatter.format(total);
    }
    public void loadDataCategories(){
        mainPresenter.loadDataCategories();
    }

    public void updateCart(int event, int idCategory, int idProduct){
        mainPresenter.updateItemCart(event,idCategory,idProduct,this);
        //mainPresenter.showCart();
    }

    public void showButtonCart(){
        mainPresenter.showButtonCart(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ORDER_COMPLETED && resultCode == RESULT_OK){
            loadDataCategories();
            showButtonCart();
        }
    }

    public void loadDataUserInMain(){
        nameHeader.setText(mercadoBoxPreferences.readSharedSetting("name",""));
        emailHeader.setText(mercadoBoxPreferences.readSharedSetting("email",""));
    }

}
