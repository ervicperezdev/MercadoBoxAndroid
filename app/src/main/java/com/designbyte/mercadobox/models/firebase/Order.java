package com.designbyte.mercadobox.models.firebase;

import com.designbyte.mercadobox.models.db.Cart;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {
    @SerializedName("idOrder")
    @Expose
    public int idOrder;

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("status")
    @Expose
    public int status;

    @SerializedName("date")
    @Expose
    public String date;

    @SerializedName("address")
    @Expose
    public String address;


    @SerializedName("uidUser")
    @Expose
    public String uidUser;

    @SerializedName("products")
    @Expose
    public List<Cart> products;

    public float getTotal(){
        float total = 0;
        for (Cart item : products
             ) {
            total += (item.quantity*item.costByUnit);
        }
        return total;
    }

    public float getIVA(){
        return getTotal()*0.16f;
    }
}
