package com.designbyte.mercadobox.main;

import com.designbyte.mercadobox.models.firebase.Category;
import java.util.List;

public interface MainView {
    void showProgress();
    void hideProgress();
    void loadDataCategories(List<Category> items);
    void showButtonBottomCart();
    void hideButtonBottomCart();
    void updateRecycler();
}
