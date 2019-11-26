package com.designbyte.mercadobox.cart;

import com.designbyte.mercadobox.models.db.Cart;
import com.designbyte.mercadobox.models.firebase.Category;

import java.util.List;

public interface CartView {
    void onContinueOrder();
    void showProgressDialog();
    void hideProgressDialog();
    void loadDataCart(List<Cart> items);

    void onRemoveSuccess();
}
