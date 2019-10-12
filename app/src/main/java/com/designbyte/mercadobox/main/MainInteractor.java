package com.designbyte.mercadobox.main;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import com.designbyte.mercadobox.models.firebase.Category;
import com.designbyte.mercadobox.models.firebase.Product;
import com.designbyte.mercadobox.models.db.AppDatabase;
import com.designbyte.mercadobox.models.db.Cart;
import com.designbyte.mercadobox.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import static androidx.room.Room.databaseBuilder;


public class MainInteractor {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference categories;
    List<Category> categoryList;
    AppDatabase db;
    Cart newCart;


    interface OnMainListener{
        void onLogout();
        void setDataCategories(List<Category> items);
        void onCompleteUpdated();
        void showCart();
        void hideCart();

    }

    public void updateItemCart(int event, final int idCategory, final int idProduct, Context context, final OnMainListener listener) {
         db = databaseBuilder(context,
                AppDatabase.class, "mbdb").allowMainThreadQueries().build();
        categories = database.getReference("Categories");
        Cart item = db.cartDao().getItemCart(idCategory,idProduct);
        switch (event){
            case Constants.PRODUCT_ADD:
                newCart = new Cart();
                categories.child(String.valueOf(idCategory)).child("products").child(String.valueOf(idProduct)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Product product = dataSnapshot.getValue(Product.class);
                        newCart.costByUnit = product.cost;
                        newCart.step = product.step;
                        newCart.unity = product.unity;
                        newCart.idCategory = idCategory;
                        newCart.idProduct = idProduct;
                        newCart.image = product.img;
                        newCart.quantity = 1;
                        newCart.name = product.name;
                        newCart.description = "";
                        db.cartDao().insertItem(newCart);
                        listener.onCompleteUpdated();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case Constants.PRODUCT_LESS:
                item.quantity--;
                if(item.quantity == 0)
                    item.quantity = 1;
                db.cartDao().updateItem(item);
                listener.onCompleteUpdated();
                break;
            case Constants.PRODUCT_PLUS:
                item.quantity++;
                db.cartDao().updateItem(item);
                listener.onCompleteUpdated();
                break;
            case Constants.PRODUCT_REMOVE:
                db.cartDao().deleteItem(item);
                listener.onCompleteUpdated();
                break;
        }
        db.close();
    }

    public void cartIsEmpty(Context context,OnMainListener listener){
        db = databaseBuilder(context,
                AppDatabase.class, "mbdb").allowMainThreadQueries().build();
        List<Cart> cartList = db.cartDao().getItemsCart();
        if(!cartList.isEmpty()){
            listener.showCart();
        }else{
            listener.hideCart();
        }
        db.close();
    }


    public void logoutApp(OnMainListener listener){
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        listener.onLogout();
    }

    public void getDataCategories(final OnMainListener listener){
        categoryList = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{

                    GenericTypeIndicator<ArrayList<Category>> t = new GenericTypeIndicator<ArrayList<Category>>() {};
                    ArrayList<Category> yourStringArray = dataSnapshot.getValue(t);
                    listener.setDataCategories(yourStringArray);

                }catch (Exception e){
                    Log.e("MainInteractor","onDataChanged"+e.getMessage());
                }finally {

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainInteractor","onCancelled"+databaseError.getMessage());
            }
        };
        database = FirebaseDatabase.getInstance();
        categories = database.getReference("Categories");
        categories.addValueEventListener(valueEventListener);
    }
}
