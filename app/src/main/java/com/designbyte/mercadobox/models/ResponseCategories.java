package com.designbyte.mercadobox.models;

import com.designbyte.mercadobox.models.firebase.Category;

import java.util.ArrayList;
import java.util.List;

public class ResponseCategories {
    public List<Category> categories = new ArrayList<>();

    public List<Category> getCategoriesById(String idCategoria) {
        List<Category> filter = new ArrayList<>();

        for (Category item: categories
             ) {
            if(item.id.contains(idCategoria)){
                filter.add(item);
                return filter;
            }

        }
        return filter;
    }

}
