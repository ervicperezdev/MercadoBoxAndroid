package com.designbyte.mercadobox.cart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.cart.ViewHolder.ViewHolderProductsCart;
import com.designbyte.mercadobox.cart.listener.RecyclerViewCartClickListener;
import com.designbyte.mercadobox.models.db.AppDatabase;
import com.designbyte.mercadobox.models.db.Cart;
import com.squareup.picasso.Picasso;

import java.util.List;

import static androidx.room.Room.databaseBuilder;

public class AdapterProductsCart extends RecyclerView.Adapter<ViewHolderProductsCart> {
    List<Cart> productCartList;
    Context context;
    RecyclerViewCartClickListener mListener;
    Cart itemCart;
    AppDatabase db;

    public AdapterProductsCart(List<Cart> productCartList, Context context, RecyclerViewCartClickListener listener) {
        this.productCartList = productCartList;
        this.context = context;
        this.mListener = listener;
        db = databaseBuilder(context,
                AppDatabase.class, "mbdb").allowMainThreadQueries().build();
    }

    @NonNull
    @Override
    public ViewHolderProductsCart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_list_product_cart,parent,false);
        return new ViewHolderProductsCart(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderProductsCart holder, final int position) {
        Picasso.get().load(productCartList.get(position).image).into(holder.imgProduct);
        holder.nameProduct.setText(productCartList.get(position).name);
        holder.descriptionProduct.setText(productCartList.get(position).description);
        holder.quantityName.setText(String.format("%s %s",productCartList.get(position).quantity,productCartList.get(position).unity));
        holder.totalProduct.setText(String.format("%s",(productCartList.get(position).costByUnit*productCartList.get(position).quantity)));

        holder.less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mListener.onClick(v,position,productCartList.get(position).id);
                itemCart = db.cartDao().getItemCartById(productCartList.get(position).id);
                holder.quantityName.setText(String.format("%s %s",itemCart.quantity,itemCart.unity));
                holder.totalProduct.setText(String.format("$%s",(itemCart.costByUnit*itemCart.quantity)));
                holder.mListener.onCostTotalChange(db.cartDao().getTotal());
            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mListener.onClick(v,position,productCartList.get(position).id);
                itemCart = db.cartDao().getItemCartById(productCartList.get(position).id);
                holder.quantityName.setText(String.format("%s %s",itemCart.quantity,itemCart.unity));
                holder.totalProduct.setText(String.format("$%s",(itemCart.costByUnit*itemCart.quantity)));
                holder.mListener.onCostTotalChange(db.cartDao().getTotal());

            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mListener.onClick(v,position,productCartList.get(position).id);
                deleteItem(position);
                holder.mListener.onCostTotalChange(db.cartDao().getTotal());
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return productCartList!=null?productCartList.size():0;
    }

    public void deleteItem(int position) {
        productCartList.remove(position);
        notifyItemRemoved(position);
    }
}
