package com.designbyte.mercadobox.cart;

import android.content.Context;

import com.designbyte.mercadobox.models.db.Cart;

import java.util.List;

public class CartPresenter implements CartInteractor.OnCartListener {
    CartView cartView;
    CartInteractor cartInteractor;

    public CartPresenter(CartView cartView, CartInteractor cartInteractor) {
        this.cartView = cartView;
        this.cartInteractor = cartInteractor;
    }

    public void loadItemsCart(Context context){
        if(cartView != null){
            cartView.showProgressDialog();
        }
        cartInteractor.loadItemsCart(context,this);
    }

    @Override
    public void setListItems(List<Cart> listItems) {
        if(cartView != null){
            cartView.hideProgressDialog();
            cartView.loadDataCart(listItems);
        }
    }

    @Override
    public void onDeleteCompleted() {
        if(cartView != null){
            cartView.hideProgressDialog();
        }
    }

    @Override
    public void onCompleteUpdated() {
        if(cartView != null){
            cartView.hideProgressDialog();
        }
    }

    @Override
    public void onRemoveAllSuccess() {
        if(cartView != null){
            cartView.onRemoveSuccess();
        }
    }

    public void deleteItemById(Context context, int id){
        if(cartView != null){
            cartView.showProgressDialog();
        }
        cartInteractor.deleteItemById(context,id,this);
    }
    public void updateItemCart(int event, int id, Context context){
        if(cartView != null){
            cartInteractor.updateItemCart(event,id,context,this);
        }
    }

    public void removeAllProducts(Context context) {
        if(cartView != null){
            cartInteractor.removeAll(context, this);
        }
    }
}
