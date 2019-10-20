package com.designbyte.mercadobox.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.main.listener.RecyclerViewProductClickListener;
import com.designbyte.mercadobox.main.viewHolder.ViewHolderProduct;
import com.designbyte.mercadobox.models.db.Cart;
import com.designbyte.mercadobox.models.firebase.Product;
import com.designbyte.mercadobox.models.db.AppDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import static androidx.room.Room.databaseBuilder;

public class AdapterProducts extends RecyclerView.Adapter<ViewHolderProduct>  {
    List<Product> listProducts;
    Context context;
    RecyclerViewProductClickListener mListener;
    AppDatabase db;
    Cart itemCart;
    public AdapterProducts(List<Product> listProducts, Context context, RecyclerViewProductClickListener listener) {
        this.listProducts = listProducts;
        this.context = context;
        this.mListener = listener;
        db = databaseBuilder(context,
                AppDatabase.class, "mbdb").allowMainThreadQueries().build();
    }

    @NonNull
    @Override
    public ViewHolderProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_list_products,parent,false);
        return new ViewHolderProduct(view,mListener);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolderProduct holder, final int position) {
        final Product product = listProducts.get(position);
        itemCart = db.cartDao().getItemCart(product.idCategory,product.id);

        //Si el producto ya existe en el carro de compras
        if(itemCart != null){
            holder.quantityProduct.setText(String.format("%s %s",itemCart.quantity,itemCart.unity));
            holder.quantityLayout.setVisibility(View.VISIBLE);
            holder.add.setVisibility(View.GONE);
        }else{
            holder.quantityLayout.setVisibility(View.GONE);
            holder.add.setVisibility(View.VISIBLE);
        }
        Picasso.get().load(product.img).into(holder.imgProduct);
        holder.nameProduct.setText(product.name);
        holder.costProduct.setText(String.format("%s",product.cost));
        holder.less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mListener.onClick(v,product.id,product.idCategory);
                itemCart = db.cartDao().getItemCart(product.idCategory,product.id);
                holder.quantityProduct.setText(String.format("%s %s",itemCart.quantity,itemCart.unity));
                db.close();

            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mListener.onClick(v,product.id,product.idCategory);
                itemCart = db.cartDao().getItemCart(product.idCategory,product.id);
                holder.quantityProduct.setText(String.format("%s %s",itemCart.quantity,itemCart.unity));
                db.close();

            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mListener.onClick(v,product.id,product.idCategory);
                holder.quantityLayout.setVisibility(View.VISIBLE);
                holder.add.setVisibility(View.GONE);
            }
        });
        db.close();

    }

    @Override
    public int getItemCount() {
        return listProducts!= null?listProducts.size():0;
    }
}
