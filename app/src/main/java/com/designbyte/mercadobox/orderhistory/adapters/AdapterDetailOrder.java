package com.kuppa.kuppa.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuppa.kuppa.Model.Cart.CartExtra;
import com.kuppa.kuppa.Model.Cart.CartIngredient;
import com.kuppa.kuppa.Model.Cart.CartProduct;
import com.kuppa.kuppa.R;
import com.kuppa.kuppa.ViewHolder.ItemDetailViewHolder;

import java.util.List;

public class ItemDetailOrderAdapter extends RecyclerView.Adapter<ItemDetailViewHolder> {
    List<CartProduct> items;
    Context context;
    String textExtras;
    int totalProduct, subTotalProduct;
    public ItemDetailOrderAdapter(List<CartProduct> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_detail_order,parent,false);
        return new ItemDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemDetailViewHolder holder, int position) {
        holder.tvName.setText(items.get(position).getProductName());
        totalProduct = 0;
        subTotalProduct = 0;
        String textExtras = "";
        if(items.get(position).getExtras() != null) {
            for (CartExtra cartExtra : items.get(position).getExtras()) {
                for (CartIngredient cartIngredient : cartExtra.getListIngredients()) {
                    textExtras += cartIngredient.getName() + "(" + String.format("$%s.00", cartIngredient.getPrice() * Integer.valueOf(items.get(position).getQuantity())) + ")" + "\n";
                    subTotalProduct += cartIngredient.getPrice();
                }
            }
        }
        holder.totalProduct.setText(String.format("$%s.00",(Integer.valueOf(items.get(position).getPrice())+subTotalProduct)*Integer.valueOf(items.get(position).getQuantity())));
        holder.tvQuantity.setText(items.get(position).getQuantity()+"X");
        holder.tvExtras.setText(textExtras);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size():0;
    }


}
