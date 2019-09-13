package com.designbyte.mercadobox.cart;

public class CartPresenter {

    CartView cartView;
    CartInteractor cartInteractor;

    public CartPresenter(CartView cartView, CartInteractor cartInteractor) {
        this.cartView = cartView;
        this.cartInteractor = cartInteractor;
    }


    public void loadItemsCart(){

    }

}
