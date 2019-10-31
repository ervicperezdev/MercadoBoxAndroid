package com.designbyte.mercadobox.main.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.main.listener.RecyclerViewFilterClickListener;
import com.designbyte.mercadobox.main.viewHolder.ViewHolderFilterCategory;
import com.designbyte.mercadobox.models.firebase.Category;

import java.util.List;

public class AdapterFilterCategories extends RecyclerView.Adapter<ViewHolderFilterCategory> {
    List<Category> categoryList;
    Context context;
    RecyclerViewFilterClickListener mListener;
    int index_row = 0;
    public AdapterFilterCategories(List<Category> categoryList, Context context, RecyclerViewFilterClickListener listener) {
        this.categoryList = categoryList;
        this.context = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolderFilterCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_list_filter_category,parent,false);
        return new ViewHolderFilterCategory(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderFilterCategory holder, final int position) {
        final Category category = categoryList.get(position);
        holder.nameCategory.setText(category.nameCategory);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                index_row = position;
                holder.mListener.onClick(v,position,category.id);
            }
        });

        if(index_row != position){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.cardCategory.setCardBackgroundColor(context.getColor(R.color.grey_500));
            }else{
                holder.cardCategory.setCardBackgroundColor(context.getResources().getColor(R.color.grey_500));
            }
        }else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.cardCategory.setCardBackgroundColor(context.getColor(R.color.colorPrimary));
                } else {
                    holder.cardCategory.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                }


        }


    }

    @Override
    public int getItemCount() {
        return categoryList!=null?categoryList.size():0;
    }
}
