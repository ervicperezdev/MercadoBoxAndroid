package com.designbyte.mercadobox.cart;

import android.content.Context;
import com.designbyte.mercadobox.models.db.AppDatabase;
import com.designbyte.mercadobox.models.db.Cart;
import com.designbyte.mercadobox.utils.Constants;
import java.util.List;

import static androidx.room.Room.databaseBuilder;

public class CartInteractor {
    AppDatabase db;

    interface OnCartListener {
        void setListItems(List<Cart> listItems);
        void onDeleteCompleted();
        void onCompleteUpdated();
    }

    public void loadItemsCart(Context context, OnCartListener listener){
        db = databaseBuilder(context,
                AppDatabase.class, "mbdb").allowMainThreadQueries().build();
        List<Cart> items = db.cartDao().getItemsCart();
        listener.setListItems(items);
        db.close();
    }
    public void deleteItemById(Context context,int id, OnCartListener listener){
        db = databaseBuilder(context, AppDatabase.class, "mbdb").allowMainThreadQueries().build();
        db.cartDao().deleteItem(db.cartDao().getItemCartById(id));
        db.close();
        listener.onDeleteCompleted();
    }

    public void updateItemCart(int event, int idItem, Context context, OnCartListener listener){
        db = databaseBuilder(context,
                AppDatabase.class, "mbdb").allowMainThreadQueries().build();
        Cart item = db.cartDao().getItemCartById(idItem);

        switch (event){

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

}
