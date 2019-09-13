package com.designbyte.mercadobox.main.viewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.main.listener.RecyclerViewProductClickListener;


public class ViewHolderProduct extends RecyclerView.ViewHolder {
    public ImageView imgProduct;
    public TextView nameProduct, costProduct, quantityProduct;
    public Button less, plus, add;
    public RecyclerViewProductClickListener mListener;
    public RelativeLayout quantityLayout;
    public ViewHolderProduct(@NonNull View itemView, RecyclerViewProductClickListener listener) {
        super(itemView);
        imgProduct = itemView.findViewById(R.id.imgProduct);
        nameProduct = itemView.findViewById(R.id.nameProduct);
        costProduct = itemView.findViewById(R.id.costProduct);
        quantityProduct = itemView.findViewById(R.id.quantityName);
        quantityLayout = itemView.findViewById(R.id.quantityProduct);
        less = itemView.findViewById(R.id.less);
        plus  = itemView.findViewById(R.id.plus);
        add = itemView.findViewById(R.id.btnAdd);
        mListener = listener;
    }

}