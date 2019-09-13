package com.designbyte.mercadobox.cart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.models.ProductCart;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterProductsCart extends RecyclerView.Adapter<AdapterProductsCart.ViewHolderProductsCart> {
    List<ProductCart> productCartList;
    Context context;
    @NonNull
    @Override
    public ViewHolderProductsCart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_list_product_cart,parent,false);
        return new ViewHolderProductsCart(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProductsCart holder, int position) {
        Picasso.get().load(productCartList.get(position).imgProduct).into(holder.imgProduct);
        holder.nameProduct.setText(productCartList.get(position).nameProduct);
        holder.descriptionProduct.setText(productCartList.get(position).descriptionProduct);
        holder.quantityName.setText(productCartList.get(position).quantityName);
        holder.totalProduct.setText(String.format("%",productCartList.get(position).total));
        holder.less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return productCartList!=null?productCartList.size():0;
    }


    public class ViewHolderProductsCart extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView nameProduct, descriptionProduct, quantityName, totalProduct;
        Button less, plus, delete;
        public ViewHolderProductsCart(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.imgProduct);
            nameProduct = itemView.findViewById(R.id.nameProduct);
            descriptionProduct = itemView.findViewById(R.id.descriptionProduct);
            quantityName = itemView.findViewById(R.id.quantityName);
            totalProduct = itemView.findViewById(R.id.totalProduct);
            less = itemView.findViewById(R.id.less);
            plus = itemView.findViewById(R.id.plus);
            delete = itemView.findViewById(R.id.deleteProduct);
        }
    }
}
