package com.designbyte.mercadobox.main;

import android.content.Intent;
import android.os.Bundle;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.cart.CartActivity;
import com.designbyte.mercadobox.main.adapters.AdapterCategories;
import com.designbyte.mercadobox.main.listener.RecyclerViewProductClickListener;
import com.designbyte.mercadobox.models.firebase.Category;
import com.designbyte.mercadobox.orderhistory.OrderHistoryActivity;
import com.designbyte.mercadobox.profile.ProfileActivity;
import com.designbyte.mercadobox.splash.SplashActivity;
import com.designbyte.mercadobox.utils.Constants;
import com.designbyte.mercadobox.utils.MercadoBoxPreferences;

import android.view.Gravity;
import android.view.View;

import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView{
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        navigationView.setNavigationItemSelectedListener(this);
        mercadoBoxPreferences = new MercadoBoxPreferences(this);
        mainPresenter = new MainPresenter(this,new MainInteractor());
        database = FirebaseDatabase.getInstance();
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
                startActivity(new Intent(MainActivity.this, CartActivity.class));
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
        } else if (id == R.id.nav_address) {

        } else if (id == R.id.nav_preferences) {

        } else if (id == R.id.nav_logout) {
            logout();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goToProfile(){
        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
    }
    public void goToHistory(){
        startActivity(new Intent(MainActivity.this, OrderHistoryActivity.class));
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
        startActivity(new Intent(MainActivity.this,SplashActivity.class));
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
    public void loadDataCategories(List<Category> items) {
        recyclerCategories.setAdapter(new AdapterCategories(items,this,listener));
        recyclerCategories.setLayoutManager(new LinearLayoutManager(this));
        recyclerCategories.setHasFixedSize(true);
    }

    @Override
    public void showButtonBottomCart() {
        btnCart.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideButtonBottomCart() {
        btnCart.setVisibility(View.GONE);
    }

    @Override
    public void updateRecycler() {

    }

    public void loadDataCategories(){
        mainPresenter.loadDataCategories();
    }

    public void updateCart(int event, int idCategory, int idProduct){
        mainPresenter.updateItemCart(event,idCategory,idProduct,this);
        mainPresenter.showCart();
    }

    public void showButtonCart(){
        mainPresenter.showButtonCart(this);
    }
}
