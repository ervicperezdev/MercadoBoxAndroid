package com.designbyte.mercadobox.models.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartDao {

    @Query("SELECT * FROM Cart")
    List<Cart> getItemsCart();

    @Query("SELECT * FROM Cart WHERE id = :id")
    Cart getItemCartById(int id);

    @Query("SELECT * FROM Cart WHERE idCategory = :idCategory AND idProduct = :idProduct")
    Cart getItemCart(int idCategory, int idProduct);

    @Insert
    void insertItem(Cart cart);

    @Delete
    void deleteItem(Cart cart);

    @Update
    void updateItem(Cart cart);
}
