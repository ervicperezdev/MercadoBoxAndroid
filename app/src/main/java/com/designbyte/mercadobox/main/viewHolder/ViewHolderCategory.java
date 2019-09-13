package com.designbyte.mercadobox.main.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.designbyte.mercadobox.R;

public class ViewHolderCategory extends RecyclerView.ViewHolder {
    public TextView nameCategory;
    public RecyclerView recyclerViewProducts;

    public ViewHolderCategory(@NonNull View itemView) {
        super(itemView);
        nameCategory = itemView.findViewById(R.id.nameCategory);
        recyclerViewProducts = itemView.findViewById(R.id.recyclerProducts);
    }
}