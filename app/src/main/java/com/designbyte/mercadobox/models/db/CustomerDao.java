package com.designbyte.mercadobox.models.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CustomerDao {

    @Query("SELECT * FROM Customer LIMIT 1")
    Customer getCustomerLogged();

    @Query("DELETE FROM Customer")
    void deleteAll();

    @Insert
    void insertItem(Customer customer);


}
