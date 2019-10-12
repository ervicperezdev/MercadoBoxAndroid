package com.designbyte.mercadobox.main;

import android.content.Context;

import com.designbyte.mercadobox.models.db.Cart;
import com.designbyte.mercadobox.models.firebase.Category;
import java.util.List;

public class MainPresenter implements MainInteractor.OnMainListener {
    MainView mainView;
    MainInteractor mainInteractor;

    public MainPresenter(MainView mainView, MainInteractor mainInteractor) {
        this.mainView = mainView;
        this.mainInteractor = mainInteractor;
    }

    public void logout(){
        if(mainView != null){
            mainView.showProgress();
        }
        mainInteractor.logoutApp(this);
    }

    public void updateItemCart(int event, int idCategory, int idProduct, Context context){
        if(mainView != null) {
            mainInteractor.updateItemCart(event, idCategory, idProduct,context,this);
        }
    }

    public void loadDataCategories(){
        if(mainView != null){
            mainView.showProgress();
        }
        mainInteractor.getDataCategories(this);
    }

    @Override
    public void onLogout() {
        if(mainView != null){
            mainView.hideProgress();
        }
    }

    @Override
    public void setDataCategories(List<Category> items) {
        if(mainView != null){
            mainView.loadDataCategories(items);
            mainView.hideProgress();
        }
    }

    @Override
    public void onCompleteUpdated() {
        if(mainView != null){
            mainView.updateRecycler();
        }
    }

    @Override
    public void showCart(List<Cart> items) {
        if(mainView != null){
            mainView.showButtonBottomCart(items);
        }
    }


    @Override
    public void hideCart() {
        if(mainView != null){
            mainView.hideButtonBottomCart();
        }
    }

    public void showButtonCart(Context context){
        if(mainView != null){
            mainInteractor.cartIsEmpty(context,this);
        }
    }
}
