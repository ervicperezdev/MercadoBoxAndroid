package com.designbyte.mercadobox.models.firebase;

import com.designbyte.mercadobox.models.db.Cart;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {
    @SerializedName("idOrder")
    @Expose
    int idOrder;

    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("status")
    @Expose
    int status;

    @SerializedName("date")
    @Expose
    String date;

    @SerializedName("products")
    @Expose
    List<Cart> products;
}
