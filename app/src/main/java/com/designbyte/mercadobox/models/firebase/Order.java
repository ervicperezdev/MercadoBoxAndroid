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

    @SerializedName("noteOrder")
    @Expose
    public String noteOrder;

    @SerializedName("confirmationCall")
    public Boolean confirmationCall;

    public float getTotal(){
        float total = 0;
        for (Cart item : products
             ) {
            total += (item.quantity*item.costByUnit);
        }
        return total;
    }

    public String convertStatusToText(){
        String nameStatus= "";
        switch (status){
            case 0: nameStatus = "Producto en carrito";
                break;
            case 1: nameStatus = "Cotización";
                break;
            case 2: nameStatus = "Pedido confirmado";
                break;
            case 3: nameStatus = "En ruta";
                break;
            case 4: nameStatus = "Entregado";
                break;
            case 5: nameStatus = "En aclaración";
                break;
            case 6: nameStatus = "Resuelto";
                break;

        }
        return nameStatus;
    }
    public float getPorcentIVA(){
        return getTotal()*0.16f;
    }
}
