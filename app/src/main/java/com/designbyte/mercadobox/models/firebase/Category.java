package com.designbyte.mercadobox.models.firebase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Category {
    @SerializedName("idCategory")
    @Expose
    public int idCategory;
    @SerializedName("nameCategory")
    @Expose
    public String nameCategory;
    @SerializedName("products")
    @Expose
    public List<Product> products = new ArrayList<>();
}
