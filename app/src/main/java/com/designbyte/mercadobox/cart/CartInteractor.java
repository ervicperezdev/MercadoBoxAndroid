package com.designbyte.mercadobox.cart;

import android.content.Context;

import com.designbyte.mercadobox.models.db.AppDatabase;
import com.designbyte.mercadobox.models.db.Cart;

import java.util.List;

import static androidx.room.Room.databaseBuilder;

public class CartInteractor {
    AppDatabase db;

    interface OnCartListener {
        void onLoadedItems();
        void setListItems(List<Cart> listItems);
    }

    public void loadItemsCart(Context context, OnCartListener listener){
        db = databaseBuilder(context,
                AppDatabase.class, "mbdb").allowMainThreadQueries().build();
        List<Cart> items = db.cartDao().getItemsCart();
        listener.setListItems(items);
    }
}
