package com.designbyte.mercadobox.main.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.main.listener.RecyclerViewFilterClickListener;

public class ViewHolderFilterCategory extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView nameCategory;
    public CardView cardCategory;
    public RecyclerViewFilterClickListener mListener;

    public ViewHolderFilterCategory(@NonNull View itemView,RecyclerViewFilterClickListener itemClickListener) {
        super(itemView);

        nameCategory = itemView.findViewById(R.id.nameCategory);
        cardCategory = itemView.findViewById(R.id.cardCategory);
        itemView.setOnClickListener(this);
        mListener = itemClickListener;
    }


    @Override
    public void onClick(View view) {
        mListener.onClick(view,getAdapterPosition(),"");
    }
}
