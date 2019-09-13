package com.designbyte.mercadobox.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.main.listener.RecyclerViewProductClickListener;
import com.designbyte.mercadobox.main.viewHolder.ViewHolderCategory;
import com.designbyte.mercadobox.models.firebase.Category;

import java.util.List;

public class AdapterCategories extends RecyclerView.Adapter<ViewHolderCategory> {
    List<Category> categoryParentList;
    Context context;
    RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    RecyclerViewProductClickListener mListener;
    public AdapterCategories(List<Category> categoryParentList, Context context, RecyclerViewProductClickListener listener) {
        this.categoryParentList = categoryParentList;
        this.context = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_list_by_categories,parent,false);
        return new ViewHolderCategory(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCategory holder, int position) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.recyclerViewProducts.getContext(), RecyclerView.HORIZONTAL, false);
        holder.recyclerViewProducts.setLayoutManager(linearLayoutManager);
        holder.recyclerViewProducts.setHasFixedSize(true);
        holder.recyclerViewProducts.setAdapter(new AdapterProducts(categoryParentList.get(position).products,context,mListener));
        holder.recyclerViewProducts.setRecycledViewPool(viewPool);
        holder.nameCategory.setText(categoryParentList.get(position).nameCategory);
    }

    @Override
    public int getItemCount() {
        return categoryParentList != null? categoryParentList.size():0;
    }


}
