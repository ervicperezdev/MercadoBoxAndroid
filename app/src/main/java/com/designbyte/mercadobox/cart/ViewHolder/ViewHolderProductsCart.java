package com.designbyte.mercadobox.cart.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.cart.listener.RecyclerViewCartClickListener;

public class ViewHolderProductsCart extends RecyclerView.ViewHolder {
    public ImageView imgProduct;
    public TextView nameProduct, descriptionProduct, quantityName, totalProduct;
    public Button less, plus;
    public ImageView delete;
    public RecyclerViewCartClickListener mListener;
    public ViewHolderProductsCart(@NonNull View itemView, RecyclerViewCartClickListener listener) {
        super(itemView);

        imgProduct = itemView.findViewById(R.id.imgProduct);
        nameProduct = itemView.findViewById(R.id.nameProduct);
        descriptionProduct = itemView.findViewById(R.id.descriptionProduct);
        quantityName = itemView.findViewById(R.id.quantityName);
        totalProduct = itemView.findViewById(R.id.totalProduct);
        less = itemView.findViewById(R.id.less);
        plus = itemView.findViewById(R.id.plus);
        delete = itemView.findViewById(R.id.deleteProduct);
        mListener = listener;
    }
}