package com.designbyte.mercadobox.models.firebase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Category {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("nameCategory")
    @Expose
    public String nameCategory;

    @SerializedName("color")
    @Expose
    public String color;

    @SerializedName("products")
    @Expose
    public Map<String, Object> products = new HashMap<>();

    public List<Product> listProducts = new ArrayList<>();

    public void convertListProduct() {
        this.listProducts = new ArrayList<>();
        for (Object obj : products.values()) {
            if (obj instanceof Map) {
                Map<String, Object> mapObj = (Map<String, Object>) obj;
                Product product = new Product();
                product.id = (String)mapObj.get("id");
                product.idCategory = (String)mapObj.get("idCategory");
                product.nameProduct = (String)mapObj.get("nameProduct");
                product.status = (String)mapObj.get("status");
                product.inStock = (String)mapObj.get("inStock");
                product.unity = (String)mapObj.get("unity");
                product.costUnity = (String)mapObj.get("costUnity");
                product.image = (String)mapObj.get("image");

                this.listProducts.add(product);

            }
        }



    }

    public void setListProducts(List<Product> listProducts) {
        this.listProducts = listProducts;
    }

    public List<Product> getListProducts() {
        return listProducts;
    }
}
